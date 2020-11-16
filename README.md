# Лабораторная работа №3

##### студента ПИбд-21 Резникова Сергея

Задание: Разработать программу, иллюстрирующую работу диспетчера памяти и алгоритма
         замещения страниц виртуальной памяти. Необходимо указать полный объем оперативной
         памяти и размер страницы. Необходимо продемонстрировать работу с виртуальной памятью
         и работу алгоритма замещения страниц виртуальной памяти. Алгоритм FIFO использоваь
         нельзя. 


<html>
<body>
<pre>
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.2.2\lib\idea_rt.jar=56061:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.2.2\bin" -Dfile.encoding=UTF-8 -classpath C:\Users\Mvideo\IdeaProjects\OS_Laboratory3\out\production\OS_Laboratory3 Main
Program is running now!
Process PID = 0 is created (its size is 4). It includes:
Page ID = 0
Page ID = 1
Page ID = 2
Page ID = 3
Process PID = 1 is created (its size is 2). It includes:
Page ID = 4
Page ID = 5
Process PID = 2 is created (its size is 2). It includes:
Page ID = 6
Page ID = 7
Process PID = 3 is created (its size is 2). It includes:
Page ID = 8
Page ID = 9
Process PID = 4 is created (its size is 3). It includes:
Page ID = 10
Page ID = 11
Page ID = 12
Process PID = 5 is created (its size is 2). It includes:
Page ID = 13
Page ID = 14
Process PID = 6 is created (its size is 3). It includes:
Page ID = 15
Page ID = 16
Page ID = 17
Process PID = 7 is created (its size is 1). It includes:
Page ID = 18
Process PID = 8 is created (its size is 2). It includes:
Page ID = 19
Page ID = 20
Process PID = 9 is created (its size is 4). It includes:
Page ID = 21
Page ID = 22
Page ID = 23
Page ID = 24


Require page ID = 15
This page is not in RAM
RAM has enough place for one more page
Request was completed

Require page ID = 0
This page is not in RAM
RAM has enough place for one more page
Request was completed

Require page ID = 19
This page is not in RAM
RAM has enough place for one more page
Request was completed

Require page ID = 9
This page is not in RAM
RAM has enough place for one more page
Request was completed

Require page ID = 11
This page is not in RAM
RAM has enough place for one more page
Request was completed

Require page ID = 6
This page is not in RAM
RAM has enough place for one more page
Request was completed

Require page ID = 0
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 4
This page is not in RAM
RAM has enough place for one more page
Request was completed

Require page ID = 14
This page is not in RAM
RAM has enough place for one more page
Request was completed

Require page ID = 17
This page is not in RAM
'Swap' process
Page ID = 15 is saved in ROM
Deleting Page ID = 15 from RAM
Inserting of Page ID = 17 is complited
Changes count are changed
Request was completed

Require page ID = 16
This page is not in RAM
'Swap' process
Page ID = 19 is saved in ROM
Deleting Page ID = 19 from RAM
Inserting of Page ID = 16 is complited
Changes count are changed
Request was completed

Require page ID = 20
This page is not in RAM
'Swap' process
Page ID = 9 is saved in ROM
Deleting Page ID = 9 from RAM
Inserting of Page ID = 20 is complited
Changes count are changed
Request was completed

Require page ID = 13
This page is not in RAM
'Swap' process
Page ID = 11 is saved in ROM
Deleting Page ID = 11 from RAM
Inserting of Page ID = 13 is complited
Changes count are changed
Request was completed

Require page ID = 24
This page is not in RAM
'Swap' process
Page ID = 6 is saved in ROM
Deleting Page ID = 6 from RAM
Inserting of Page ID = 24 is complited
Changes count are changed
Request was completed

Require page ID = 20
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 18
This page is not in RAM
'Swap' process
Page ID = 4 is saved in ROM
Deleting Page ID = 4 from RAM
Inserting of Page ID = 18 is complited
Changes count are changed
Request was completed

Require page ID = 17
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 7
This page is not in RAM
'Swap' process
Page ID = 14 is saved in ROM
Deleting Page ID = 14 from RAM
Inserting of Page ID = 7 is complited
Changes count are changed
Request was completed

Require page ID = 19
This page is not in RAM
'Swap' process
Page ID = 0 is saved in ROM
Deleting Page ID = 0 from RAM
Inserting of Page ID = 19 is complited
Changes count are changed
Request was completed

Require page ID = 17
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 5
This page is not in RAM
'Swap' process
Page ID = 16 is saved in ROM
Deleting Page ID = 16 from RAM
Inserting of Page ID = 5 is complited
Changes count are changed
Request was completed

Require page ID = 0
This page is not in RAM
'Swap' process
Page ID = 17 is saved in ROM
Deleting Page ID = 17 from RAM
Inserting of Page ID = 0 is complited
Changes count are changed
Request was completed

Require page ID = 22
This page is not in RAM
'Swap' process
Page ID = 20 is saved in ROM
Deleting Page ID = 20 from RAM
Inserting of Page ID = 22 is complited
Changes count are changed
Request was completed

Require page ID = 12
This page is not in RAM
'Swap' process
Page ID = 13 is saved in ROM
Deleting Page ID = 13 from RAM
Inserting of Page ID = 12 is complited
Changes count are changed
Request was completed

Require page ID = 8
This page is not in RAM
'Swap' process
Page ID = 24 is saved in ROM
Deleting Page ID = 24 from RAM
Inserting of Page ID = 8 is complited
Changes count are changed
Request was completed

Require page ID = 14
This page is not in RAM
'Swap' process
Page ID = 18 is saved in ROM
Deleting Page ID = 18 from RAM
Inserting of Page ID = 14 is complited
Changes count are changed
Request was completed

Require page ID = 19
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 22
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 0
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 5
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 21
This page is not in RAM
'Swap' process
Page ID = 7 is saved in ROM
Deleting Page ID = 7 from RAM
Inserting of Page ID = 21 is complited
Changes count are changed
Request was completed

Require page ID = 6
This page is not in RAM
'Swap' process
Page ID = 19 is saved in ROM
Deleting Page ID = 19 from RAM
Inserting of Page ID = 6 is complited
Changes count are changed
Request was completed

Require page ID = 24
This page is not in RAM
'Swap' process
Page ID = 5 is saved in ROM
Deleting Page ID = 5 from RAM
Inserting of Page ID = 24 is complited
Changes count are changed
Request was completed

Require page ID = 23
This page is not in RAM
'Swap' process
Page ID = 0 is saved in ROM
Deleting Page ID = 0 from RAM
Inserting of Page ID = 23 is complited
Changes count are changed
Request was completed

Require page ID = 14
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 10
This page is not in RAM
'Swap' process
Page ID = 22 is saved in ROM
Deleting Page ID = 22 from RAM
Inserting of Page ID = 10 is complited
Changes count are changed
Request was completed

Require page ID = 0
This page is not in RAM
'Swap' process
Page ID = 12 is saved in ROM
Deleting Page ID = 12 from RAM
Inserting of Page ID = 0 is complited
Changes count are changed
Request was completed

Require page ID = 17
This page is not in RAM
'Swap' process
Page ID = 8 is saved in ROM
Deleting Page ID = 8 from RAM
Inserting of Page ID = 17 is complited
Changes count are changed
Request was completed

Require page ID = 19
This page is not in RAM
'Swap' process
Page ID = 14 is saved in ROM
Deleting Page ID = 14 from RAM
Inserting of Page ID = 19 is complited
Changes count are changed
Request was completed

Require page ID = 17
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 17
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 15
This page is not in RAM
'Swap' process
Page ID = 21 is saved in ROM
Deleting Page ID = 21 from RAM
Inserting of Page ID = 15 is complited
Changes count are changed
Request was completed

Require page ID = 0
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 9
This page is not in RAM
'Swap' process
Page ID = 6 is saved in ROM
Deleting Page ID = 6 from RAM
Inserting of Page ID = 9 is complited
Changes count are changed
Request was completed

Require page ID = 16
This page is not in RAM
'Swap' process
Page ID = 24 is saved in ROM
Deleting Page ID = 24 from RAM
Inserting of Page ID = 16 is complited
Changes count are changed
Request was completed

Require page ID = 22
This page is not in RAM
'Swap' process
Page ID = 23 is saved in ROM
Deleting Page ID = 23 from RAM
Inserting of Page ID = 22 is complited
Changes count are changed
Request was completed

Require page ID = 10
this page is already in RAM
Changes indicator is increased
Request was completed

Require page ID = 20
This page is not in RAM
'Swap' process
Page ID = 10 is saved in ROM
Deleting Page ID = 10 from RAM
Inserting of Page ID = 20 is complited
Changes count are changed
Request was completed

Require page ID = 6
This page is not in RAM
'Swap' process
Page ID = 0 is saved in ROM
Deleting Page ID = 0 from RAM
Inserting of Page ID = 6 is complited
Changes count are changed
Request was completed

Require page ID = 16
this page is already in RAM
Changes indicator is increased
Request was completed


Process finished with exit code 0
</pre>
</body>
</html>

