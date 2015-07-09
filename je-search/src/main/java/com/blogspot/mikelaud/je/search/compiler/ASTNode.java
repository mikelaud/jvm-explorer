/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Matt McCutchen - partial fix for https://bugs.eclipse.org/bugs/show_bug.cgi?id=122995
 *     Karen Moore - fix for https://bugs.eclipse.org/bugs/show_bug.cgi?id=207411
 *     Stephan Herrmann <stephan@cs.tu-berlin.de> - Contributions for 
 *     							bug 185682 - Increment/decrement operators mark local variables as read
 *     							bug 186342 - [compiler][null] Using annotations for null checking
 *								bug 365519 - editorial cleanup after bug 186342 and bug 365387
 *								bug 374605 - Unreasonable warning for enum-based switch statements
 *								bug 384870 - [compiler] @Deprecated annotation not detected if preceded by other annotation
 *								bug 393719 - [compiler] inconsistent warnings on iteration variables
 *								Bug 392099 - [1.8][compiler][null] Apply null annotation on types for null analysis
 *								Bug 417295 - [1.8[[null] Massage type annotated null analysis to gel well with deep encoded type bindings.
 *								Bug 400874 - [1.8][compiler] Inference infrastructure should evolve to meet JLS8 18.x (Part G of JSR335 spec)
 *								Bug 424742 - [1.8] NPE in LambdaExpression.isCompatibleWith
 *								Bug 424710 - [1.8][compiler] CCE in SingleNameReference.localVariableBinding
 *								Bug 424205 - [1.8] Cannot infer type for diamond type with lambda on method invocation
 *								Bug 424415 - [1.8][compiler] Eventual resolution of ReferenceExpression is not seen to be happening.
 *								Bug 426366 - [1.8][compiler] Type inference doesn't handle multiple candidate target types in outer overload context
 *								Bug 427282 - [1.8][compiler] AIOOB (-1) at org.eclipse.jdt.internal.compiler.ClassFile.traverse(ClassFile.java:6209)
 *								Bug 427483 - [Java 8] Variables in lambdas sometimes can't be resolved
 *								Bug 428352 - [1.8][compiler] Resolution errors don't always surface
 *								Bug 427163 - [1.8][null] bogus error "Contradictory null specification" on varags
 *								Bug 432348 - [1.8] Internal compiler error (NPE) after upgrade to 1.8
 *								Bug 440143 - [1.8][null] one more case of contradictory null annotations regarding type variables
 *								Bug 441693 - [1.8][null] Bogus warning for type argument annotated with @NonNull
 *								Bug 434483 - [1.8][compiler][inference] Type inference not picked up with method reference
 *								Bug 446442 - [1.8] merge null annotations from super methods
 *								Bug 437072 - [compiler][null] Null analysis emits possibly incorrect warning for new int[][] despite @NonNullByDefault 
 *     Jesper S Moller - Contributions for
 *								bug 382721 - [1.8][compiler] Effectively final variables needs special treatment
 *								bug 412153 - [1.8][compiler] Check validity of annotations which may be repeatable
 *								bug 412153 - [1.8][compiler] Check validity of annotations which may be repeatable
 *								bug 412149 - [1.8][compiler] Emit repeated annotations into the designated container
 *								bug 419209 - [1.8] Repeating container annotations should be rejected in the presence of annotation it contains
 *******************************************************************************/
package com.blogspot.mikelaud.je.search.compiler;

/*
 * (reduced)
 */
public abstract class ASTNode {

	// storage for internal flags (32 bits)				BIT USAGE
	public final static int Bit1 = 0x1;					// return type (operator) | name reference kind (name ref) | add assertion (type decl) | useful empty statement (empty statement)
	public final static int Bit2 = 0x2;					// return type (operator) | name reference kind (name ref) | has local type (type, method, field decl) | if type elided (local)
	public final static int Bit3 = 0x4;					// return type (operator) | name reference kind (name ref) | implicit this (this ref) | is argument(local)
	public final static int Bit4 = 0x8;					// return type (operator) | first assignment to local (name ref,local decl) | undocumented empty block (block, type and method decl)
	public final static int Bit5 = 0x10;					// value for return (expression) | has all method bodies (unit) | supertype ref (type ref) | resolved (field decl)
	public final static int Bit6 = 0x20;					// depth (name ref, msg) | ignore need cast check (cast expression) | error in signature (method declaration/ initializer) | is recovered (annotation reference)
	public final static int Bit7 = 0x40;					// depth (name ref, msg) | operator (operator) | need runtime checkcast (cast expression) | label used (labelStatement) | needFreeReturn (AbstractMethodDeclaration)
	public final static int Bit8 = 0x80;					// depth (name ref, msg) | operator (operator) | unsafe cast (cast expression) | is default constructor (constructor declaration) | isElseStatementUnreachable (if statement)
	public final static int Bit9 = 0x100;				// depth (name ref, msg) | operator (operator) | is local type (type decl) | isThenStatementUnreachable (if statement) | can be static
	public final static int Bit10= 0x200;				// depth (name ref, msg) | operator (operator) | is anonymous type (type decl)
	public final static int Bit11 = 0x400;				// depth (name ref, msg) | operator (operator) | is member type (type decl)
	public final static int Bit12 = 0x800;				// depth (name ref, msg) | operator (operator) | has abstract methods (type decl)
	public final static int Bit13 = 0x1000;			// depth (name ref, msg) | is secondary type (type decl)
	public final static int Bit14 = 0x2000;			// strictly assigned (reference lhs) | discard enclosing instance (explicit constr call) | hasBeenGenerated (type decl)
	public final static int Bit15 = 0x4000;			// is unnecessary cast (expression) | is varargs (type ref) | isSubRoutineEscaping (try statement) | superAccess (javadoc allocation expression/javadoc message send/javadoc return statement)
	public final static int Bit16 = 0x8000;			// in javadoc comment (name ref, type ref, msg)
	public final static int Bit17 = 0x10000;			// compound assigned (reference lhs) | unchecked (msg, alloc, explicit constr call)
	public final static int Bit18 = 0x20000;			// non null (expression) | onDemand (import reference)
	public final static int Bit19 = 0x40000;			// didResolve (parameterized qualified type ref/parameterized single type ref)  | empty (javadoc return statement) | needReceiverGenericCast (msg/fieldref)
	public final static int Bit20 = 0x80000;			// contains syntax errors (method declaration, type declaration, field declarations, initializer), typeref: <> name ref: lambda capture)
	public final static int Bit21 = 0x100000;
	public final static int Bit22 = 0x200000;			// parenthesis count (expression) | used (import reference) shadows outer local (local declarations)
	public final static int Bit23 = 0x400000;			// parenthesis count (expression)
	public final static int Bit24 = 0x800000;			// parenthesis count (expression)
	public final static int Bit25 = 0x1000000;		// parenthesis count (expression)
	public final static int Bit26 = 0x2000000;		// parenthesis count (expression)
	public final static int Bit27 = 0x4000000;		// parenthesis count (expression)
	public final static int Bit28 = 0x8000000;		// parenthesis count (expression)
	public final static int Bit29 = 0x10000000;		// parenthesis count (expression)
	public final static int Bit30 = 0x20000000;		// elseif (if statement) | try block exit (try statement) | fall-through (case statement) | ignore no effect assign (expression ref) | needScope (for statement) | isAnySubRoutineEscaping (return statement) | blockExit (synchronized statement)
	public final static int Bit31 = 0x40000000;		// local declaration reachable (local decl) | ignore raw type check (type ref) | discard entire assignment (assignment) | isSynchronized (return statement) | thenExit (if statement)
	public final static int Bit32 = 0x80000000;		// reachable (statement)

	public final static long Bit32L = 0x80000000L;
	public final static long Bit33L = 0x100000000L;
	public final static long Bit34L = 0x200000000L;
	public final static long Bit35L = 0x400000000L;
	public final static long Bit36L = 0x800000000L;
	public final static long Bit37L = 0x1000000000L;
	public final static long Bit38L = 0x2000000000L;
	public final static long Bit39L = 0x4000000000L;
	public final static long Bit40L = 0x8000000000L;
	public final static long Bit41L = 0x10000000000L;
	public final static long Bit42L = 0x20000000000L;
	public final static long Bit43L = 0x40000000000L;
	public final static long Bit44L = 0x80000000000L;
	public final static long Bit45L = 0x100000000000L;
	public final static long Bit46L = 0x200000000000L;
	public final static long Bit47L = 0x400000000000L;
	public final static long Bit48L = 0x800000000000L;
	public final static long Bit49L = 0x1000000000000L;
	public final static long Bit50L = 0x2000000000000L;
	public final static long Bit51L = 0x4000000000000L;
	public final static long Bit52L = 0x8000000000000L;
	public final static long Bit53L = 0x10000000000000L;
	public final static long Bit54L = 0x20000000000000L;
	public final static long Bit55L = 0x40000000000000L;
	public final static long Bit56L = 0x80000000000000L;
	public final static long Bit57L = 0x100000000000000L;
	public final static long Bit58L = 0x200000000000000L;
	public final static long Bit59L = 0x400000000000000L;
	public final static long Bit60L = 0x800000000000000L;
	public final static long Bit61L = 0x1000000000000000L;
	public final static long Bit62L = 0x2000000000000000L;
	public final static long Bit63L = 0x4000000000000000L;
	public final static long Bit64L = 0x8000000000000000L;
}
