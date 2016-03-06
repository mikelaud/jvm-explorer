/*******************************************************************************
 * Copyright (c) 2005, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     David Foerster - patch for toUpperCase as described in https://bugs.eclipse.org/bugs/show_bug.cgi?id=153125
 *******************************************************************************/
package com.blogspot.mikelaud.je.search.compiler;

/*
 * (reduced)
 */
public class ScannerHelper {

	public final static long[] Bits = {
		ASTNode.Bit1, ASTNode.Bit2, ASTNode.Bit3, ASTNode.Bit4, ASTNode.Bit5, ASTNode.Bit6,
		ASTNode.Bit7, ASTNode.Bit8, ASTNode.Bit9, ASTNode.Bit10, ASTNode.Bit11, ASTNode.Bit12,
		ASTNode.Bit13, ASTNode.Bit14, ASTNode.Bit15, ASTNode.Bit16, ASTNode.Bit17, ASTNode.Bit18,
		ASTNode.Bit19, ASTNode.Bit20, ASTNode.Bit21, ASTNode.Bit22, ASTNode.Bit23, ASTNode.Bit24,
		ASTNode.Bit25, ASTNode.Bit26, ASTNode.Bit27, ASTNode.Bit28, ASTNode.Bit29, ASTNode.Bit30,
		ASTNode.Bit31, ASTNode.Bit32L, ASTNode.Bit33L, ASTNode.Bit34L, ASTNode.Bit35L, ASTNode.Bit36L,
		ASTNode.Bit37L, ASTNode.Bit38L, ASTNode.Bit39L, ASTNode.Bit40L, ASTNode.Bit41L, ASTNode.Bit42L,
		ASTNode.Bit43L, ASTNode.Bit44L, ASTNode.Bit45L, ASTNode.Bit46L, ASTNode.Bit47L, ASTNode.Bit48L,
		ASTNode.Bit49L, ASTNode.Bit50L, ASTNode.Bit51L, ASTNode.Bit52L, ASTNode.Bit53L, ASTNode.Bit54L,
		ASTNode.Bit55L, ASTNode.Bit56L, ASTNode.Bit57L, ASTNode.Bit58L, ASTNode.Bit59L, ASTNode.Bit60L,
		ASTNode.Bit61L, ASTNode.Bit62L, ASTNode.Bit63L, ASTNode.Bit64L,
	};

	public final static int MAX_OBVIOUS = 128;
	public final static int[] OBVIOUS_IDENT_CHAR_NATURES = new int[MAX_OBVIOUS];

	public final static int C_JLS_SPACE = ASTNode.Bit9;
	public final static int C_SPECIAL = ASTNode.Bit8;
	public final static int C_IDENT_START = ASTNode.Bit7;
	public final static int C_UPPER_LETTER = ASTNode.Bit6;
	public final static int C_LOWER_LETTER = ASTNode.Bit5;
	public final static int C_IDENT_PART = ASTNode.Bit4;
	public final static int C_DIGIT = ASTNode.Bit3;
	public final static int C_SEPARATOR = ASTNode.Bit2;
	public final static int C_SPACE = ASTNode.Bit1;

	static {
		OBVIOUS_IDENT_CHAR_NATURES[0] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[1] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[2] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[3] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[4] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[5] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[6] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[7] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[8] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[14] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[15] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[16] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[17] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[18] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[19] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[20] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[21] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[22] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[23] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[24] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[25] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[26] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[27] = C_IDENT_PART;
		OBVIOUS_IDENT_CHAR_NATURES[127] = C_IDENT_PART;

		for (int i = '0'; i <= '9'; i++)
			OBVIOUS_IDENT_CHAR_NATURES[i] = C_DIGIT | C_IDENT_PART;

		for (int i = 'a'; i <= 'z'; i++)
			OBVIOUS_IDENT_CHAR_NATURES[i] = C_LOWER_LETTER | C_IDENT_PART | C_IDENT_START;
		for (int i = 'A'; i <= 'Z'; i++)
			OBVIOUS_IDENT_CHAR_NATURES[i] = C_UPPER_LETTER | C_IDENT_PART | C_IDENT_START;

		OBVIOUS_IDENT_CHAR_NATURES['_'] = C_SPECIAL | C_IDENT_PART | C_IDENT_START;
		OBVIOUS_IDENT_CHAR_NATURES['$'] = C_SPECIAL | C_IDENT_PART | C_IDENT_START;

		OBVIOUS_IDENT_CHAR_NATURES[9] = C_SPACE | C_JLS_SPACE; // \ u0009: HORIZONTAL TABULATION
		OBVIOUS_IDENT_CHAR_NATURES[10] = C_SPACE | C_JLS_SPACE; // \ u000a: LINE FEED
		OBVIOUS_IDENT_CHAR_NATURES[11] = C_SPACE;
		OBVIOUS_IDENT_CHAR_NATURES[12] = C_SPACE | C_JLS_SPACE; // \ u000c: FORM FEED
		OBVIOUS_IDENT_CHAR_NATURES[13] = C_SPACE | C_JLS_SPACE; //  \ u000d: CARRIAGE RETURN
		OBVIOUS_IDENT_CHAR_NATURES[28] = C_SPACE;
		OBVIOUS_IDENT_CHAR_NATURES[29] = C_SPACE;
		OBVIOUS_IDENT_CHAR_NATURES[30] = C_SPACE;
		OBVIOUS_IDENT_CHAR_NATURES[31] = C_SPACE;
		OBVIOUS_IDENT_CHAR_NATURES[32] = C_SPACE | C_JLS_SPACE; //  \ u0020: SPACE

		OBVIOUS_IDENT_CHAR_NATURES['.'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES[':'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES[';'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES[','] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['['] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES[']'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['('] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES[')'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['{'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['}'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['+'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['-'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['*'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['/'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['='] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['&'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['|'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['?'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['<'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['>'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['!'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['%'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['^'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['~'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['"'] = C_SEPARATOR;
		OBVIOUS_IDENT_CHAR_NATURES['\''] = C_SEPARATOR;
	}
	
	public static char toUpperCase(char c) {
		if (c < MAX_OBVIOUS) {
			if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & ScannerHelper.C_UPPER_LETTER) != 0) {
				return c;
			} else if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & ScannerHelper.C_LOWER_LETTER) != 0) {
				return (char) (c - 32);
			}
		}
		return Character.toUpperCase(c);
	}
	
	public static char toLowerCase(char c) {
		if (c < MAX_OBVIOUS) {
			if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & ScannerHelper.C_LOWER_LETTER) != 0) {
				return c;
			} else if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & ScannerHelper.C_UPPER_LETTER) != 0) {
				return (char) (32 + c);
			}
		}
		return Character.toLowerCase(c);
	}

}
