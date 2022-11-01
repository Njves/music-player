# ������������ ������ �2 "������� ������"

���������� ������� ������ � ����������� �����������. �������������
��������� ����������:
1) �������� ���������;
2) �������� ���������;
3) ���������� ����������� ���������� � ��������;
4) �������� ����������� ���������� �� ���������;
5) ����������� ����������� ���������� �� ������ ������� ��������� (��������� �������);
6) ������������ ����������� ����������;
7) ������ ���������� ����������� ����������;
8) ������ ����������� ����������� ����������;
9) ����� ���������� ������������ ��������� ���������� � ��������� ��
������ ���������� �������.

���������:
> ����������� ��������� ���������� ������

##  ���������� � ����������

���������� ����� `Composition`, �������������� ����������� ����������, �
����� `PlayList`, ����������� ������ ������ ���������. ����� `PlayList`
����������� �� �������� ������ `LinkedList`, ������������ �������� ���
������� �������:
- `append_left(self, item)` - ���������� �������� � ������ ������;
- `append_right(self, item)` - ���������� �������� � ����� ������;
- `append(self, item)` - ����� ��� `append_right`;
- `remove(self, item)` - �������� ��������, ��� ��� ���������� � ������
������ ���������� ���������� `ValueError`;
- `insert(self, previous, item)` - ������� �������� `item` �����
�������� `previous`.
- `last(self)` - ��������� ���������� �������� ������

�������� ��������� "����������" ������� � ������ `LinkedList`:
- `__len__` - ����� ������;
- `__iter__` - ��������� ���������;
- `__next__` - ��������� ���������� ��������;
- `__getitem__` - ��������� �������� �� �������;
- `__contains__` - ��������� ��������� `in`;
- `__reversed__` - ��������� ������� `reversed`.

�������� �������� ������ ���������� � ���� ������ `LinkedListItem`,
������� ����� ��������� ������ �� ��������� � ���������� ��������, �
����� ������ � ���� ���������� `Composition`.

���������� ������ � ���������� � ����������� �������� � `LinkedListItem`
����� �������� (`getter` � `setter`):
- `next_item`
- `previous_item`

����� ��� �� �������� ����� ����������� ���������� ��������� � ������.

������������� ��������� ������ � ������ `PlayList`:
- `play_all(self, item)` - ������ ����������� ��� �����, ������� � `item`;
- `next_track(self)` - ������� � ���������� �����;
- `previous_track(self)` - ������� � ����������� �����;
- `current(self)` - �������� ������ ����, ����������� � ���� ��������.

����������� ���������������� ��������� ����� �� ����� ���������� (���
��� �������), �������� `PyQt` ��� `Flask`. �������������
`QMediaPlaylist` ���������� `PyQt` ��� ���������� ��������� ��
�����������.

� ������ `linked_list.py` ��������� ���������� ������� ������� �
�������. � ����� `test_linked_list.py` �� ������� ����� ��� ��������
�������. ����� ����� ��������� � ������� ������ `unittest` ��� `pylint`.

���������:
> ������������ ����������� ���������� ����� ����������� � �������
> `pygame`.

> ��������� ��������� �������

![��������� �������](../../image/class_diagram.png)

<!-- ## ������� � �������� ������ -->

## �������� ����������

������ ������������ � ������������ �� ���������� ������������:

1) ����� ����������:
    - ��� ������ �������� �������� �������� `pylint` � ����������������
    ������ `.pylintrc`.
    - ��� ������ ������� �������� �����, ���� ������� �������.
    - ������� ������������ � �������, ��������, ������� � �������.
    - ������� ��������� �����.
2) �� ������ 3 �����:
    - ��������� ������������ ������ ���� ��������;
    - ����������� ������ 3, 4, 6, 7 � 8.
3) �� ������ 4 �����:
    - ������������� ����������� ������ 5 � 9.
4) �� ������ 5 �����:
    - ����������� ��� ������, ��������� � �������� � ������.

## �������� ���������

- [Multiply two numbers represented by Linked Lists](https://www.geeksforgeeks.org/multiply-two-numbers-represented-linked-lists/)
- [Data Structures In The Real World � Linked List](https://medium.com/journey-of-one-thousand-apps/data-structures-in-the-real-world-508f5968545a)
- [How is Python's List Implemented?](https://stackoverflow.com/questions/3917574/how-is-pythons-list-implemented)
- [Playing mp3 song on python](https://stackoverflow.com/questions/20021457/playing-mp3-song-on-python)
- [��������� �������](https://en.wikipedia.org/wiki/Class_diagram)