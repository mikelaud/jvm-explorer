Log sample:
-----------
```
[ssh]: ssh root@192.168.10.101:22
[ssh]: ls -l
total 4189944
-rw-------. 1 root root         796 Jun 16  2015 anaconda-ks.cfg
drwxrwsr-x. 7  100 users       4096 Feb 19  2015 eclipse
-rw-r--r--. 1 root root    27111487 Jun 16  2015 eclipse-java-luna-SR2-linux-gtk-x86_64.tar.gz
-rw-r--r--. 1 root root      244736 Oct 30 03:19 notepad2.exe
-rw-r--r--. 1 root root           0 Feb 19 15:23 notepad3.exe
-rw-r--r--. 1 root root      244736 Oct 30 03:19 notepad.exe
-rw-r--r--. 1 root root  4262884210 Feb 22 19:16 test.rar
[ssh]: pwd
/root
[ssh]: scp C:\Windows\notepad.exe root@192.168.10.101:/root/notepad.exe
[ssh]: check: root@192.168.10.101:/root/notepad.exe
[ssh]: scp C:\Windows\notepad.exe root@192.168.10.101:/root/notepad2.exe
[ssh]: check: root@192.168.10.101:/root/notepad2.exe
[ssh]: ls
anaconda-ks.cfg
eclipse
eclipse-java-luna-SR2-linux-gtk-x86_64.tar.gz
notepad2.exe
notepad3.exe
notepad.exe
test.rar
[ssh]: scp root@192.168.10.101:/root/notepad2.exe C:\Users\Mykhailo\notepad2.exe
[ssh]: logout
```
