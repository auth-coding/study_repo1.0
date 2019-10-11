# python编程进阶

[TOC]

## 一、数据结构与算法常见问题与技巧

### 1. 在列表、字典和集合中有条件筛选数据

#### 1.1 去掉列表中的负数

```python
from random import randint
# 生成随机数组, 不需要用到的值使用_接收，明确表示此变量不会使用
random_list = [randint(-10, 10) for _ in range(20)]
>>> random_list
>>> [-1, 0, -1, -6, -8, -6, 2, -5, -1, -2, -10, 10, 5, 5, -3, 5, -7, 3, 8, -9]
```

* 方法一：直接使用for循环迭代

```python
res = []
for item in random_list:
  if item>=0:
    res.append(item)
>>> res
>>> [0, 2, 10, 5, 5, 5, 3, 8]
```

> 这种写法容易想到，但是效率不高，不够简洁，不够`pythonic`

* 方法二`[推荐]`：使用列表推导式或生成器推导式

```python
# 列表推导式
res = [i for i in random_list if i>=0]
>>> res
>>> [0, 2, 10, 5, 5, 5, 3, 8]

# 生成器推导式
res = (i for i in random_list if i>=0)
>>> res
>>> <generator object <genexpr> at 0x10624b990>
# 返回生成器对象，惰性求值，可节省空间，返回可迭代对象时效率很高，仅可迭代一次
# 可通过内建函数list，转换成列表
>>> list(res)
>>> [0, 2, 10, 5, 5, 5, 3, 8]
```

* 方法三：使用内置函数`filter`

```python
# filter(function or None, iterable)
g = filter(lambda item:item>=0, random_list)
# 传入匿名函数，接受一个参数，返回参数是否大于零的布尔值
>>> g
>>> <filter at 0x10626b898> 
# 返回filter对象, filter对象和generator对象都是Iterator对象的子类
>>> from typing import Iterator
>>> isinstance(g, Iterator) and isinstance(res, Iterator)
>>> True
>>> issubclass(type(g), Iterator) and issubclass(type(res), Iterator)
>>> True

# 将filter对象转换成列表
>>> list(g)
>>> [0, 2, 10, 5, 5, 5, 3, 8]
# 再次转换, filter对象已经迭代到最后，无对象可迭代了
>>> list(g)
>>> []
```



#### 1.2 筛选学生成绩字典大于90的项

直接使用for循环筛选的方式简单明了，但是不够简洁和pythonic，就不再赘述，筛选条件简单，直接使用推导式，筛选条件复杂可以选用filter函数(将筛选条件封装成函数)或者直接使用for循环。

> 其实选用for循环，类似算法里选用穷举法，实在算不得高明，但是却不可忽略，穷举法是对问题最直观的考量，是解决问题的可选方法之一，在实际应用中应首先考虑可行性和实现成本，再考虑扩展性、优雅型、高可用性。学习中则主要以学习新的知识为主。

```python
# 生成随机学生成绩字典--10人
from random import randint
# 字典推导式
scores = {'student%d' % i : randint(50, 100) for i in range(1,21)}
>>> scores
>>> {'student1': 74, 'student2': 51, 'student3': 56, 'student4': 70, 'student5': 91, 'student6': 64, 'student7': 58, 'student8': 79, 'student9': 89, 'student10': 85, 'student11': 83, 'student12': 77, 'student13': 91, 'student14': 100, 'student15': 66, 'student16': 88, 'student17': 83, 'student18': 50, 'student19': 52, 'student20': 94}
# python3.6及其以后的版本，底层的cpython实现已经默认字典按插入顺序有序，但是ipython可能由于底层实现不同，并不是有序字典，所以此特性暂时不能当做语言规范

```

* 方法一`[推荐]`： 使用字典推导式

```python
high_scores = {k:v for k,v in scores.items() if v >= 90 }
# dict.items, 返回可迭代对象，每项是一个元组(key, value)
>>> high_scores
>>> {'student5': 91, 'student13': 91, 'student14': 100, 'student20': 94}
```

* 方法二：使用filter函数

```python
f = filter(lambda item: item[1]>=90, scores.items())
# 将filter转换成列表，可以发现结果就是对字典的items方法返回结果的筛选
>>> list(f)
>>> [('student5', 91), ('student13', 91), ('student14', 100), ('student20', 94)]
# 重新获取filter对象
f = filter(lambda item: item[1]>=90, scores.items())
# 转换成字典
>>> dict(f)
>>> {'student5': 91, 'student13': 91, 'student14': 100, 'student20': 94}
```

#### 1.3 筛选集合里能被3整除的数

```python
from random import randint
# 生成随机集合
random_set = {randint(0, 20) for _ in range(20)}
>>> random_set
>>> {2, 3, 4, 5, 7, 9, 10, 12, 13, 15, 16, 18, 20}
# 集合元素不允许重复，在(0,20)之间随机20次，必然出现重复元素，也就说明随机集合不足20个元素
```

* 方法一`[推荐]`:  使用集合解析(推导)

```python
res = {i for i in random_set if i%3==0}
>>> res
>>> {3, 9, 12, 15, 18}
```

* 方法二： 使用filter内置函数

```python
res = filter(lambda i:i%3==0, random_set)
>>> set(res)
>>> {3, 9, 12, 15, 18}
```

### 2. 给元组命名提供，提供程序可读性

问题引入--为什么要给元组命令：

>* 固定格式的数据使用元组相比字典更加节省空间
>
>* 元组是不可变对象，某些场景下更加安全
>
>* 通过下标索引元组元素，不利于代码阅读和维

现有如下元组： 

```python
# (name, age, sex, email) 
student = ('liyi', 23, 'male', '163@163.com')
```



#### 2.1 定义一系列常量或者枚举常量

* 方法一：定义数值常量

```python
# 常量的定义使用大写字母
NAME, AGE, SEX, EMAIL = range(4)
# 通过以下方式访问
print(student[NAME], student[AGE], student[SEX], student[EMAIL])
>>> liyi 23 male 163@163.com
```

* 方法二：定义数值型枚举常量

```python
#  现有多个类型的元组
# teacher = (age, name) 
teacher = (44, 'cjx')
from enum import IntEnum

class StudentEnum(IntEnum):
  NAME = 0
  AGE = 1
  SEX = 2
  EMAIL = 3
class TeacherEnum(IntEnum):
  AGE = 0
  NAME = 1
  
# 通过以下方式访问
print(student[StudentEnum.NAME], student[StudentEnum.AGE])
>>> liyi 23
print(teacher[TeacherEnum.NAME], teacher[TeacherEnum.AGE])
>>> cjx 44
# 其实这些枚举类型是int的子类
>>> isinstance(Student.NAME, int)
>>> True
```

> 上述方法看似比较麻烦，但是最大优点的就是能通过见名知义的方式索引元组元素的同时，还维护了不同的命令空间。
>
> 对于python而言，实现这种小功能还需要如此麻烦，显然是不合理，正如我们所想，python对此有着更优雅的实现和更简便的接口。
>
> 而这种定义数值型枚举常量的方法，其实更多用于定义配置常量，比如
>
> errcode或者http_code, 而不用来给元组元素命名。



#### 2.2 使用collections模块下的namedtuple(命名元组)

```python
from collections = import namedtuple
Student = namedtuple('Student', ['name', 'age', 'sex', 'email'])
s1 = Student('liyi', 18, 'male', '135@qq.com')
# 返回namedtuple对象,其实是tuple的子类
>>> isinstance(s1, tuple)
>>> True
# 可以通过以下方式访问
print(s1[0], s[1], s1[2], s[3])
>>> liyi 18 male 135@qq.com
print(s1.name, s1.age, s1.sex, s1.email)
>>> liyi 18 male 135@qq.com
```

>python中的命令规范是一般函数使用小写，单词之间使用下划线隔开，类遵循首字母大写的驼峰命令法的规范。
>
>之所以说一般，是因为python中的数据型，`int`、`list`、`str`、`tuple`等，其实都是类，从```isinstance(1, int)```就可以看出，但同时又可以直接通过类名像使用函数一样调用，传入一定参数，返回该类型的数据，窃以为这个过程不能看做是函数调用，而是通过类生成对象，得到该类型的对象，所以说`int`等是内建函数是不太恰当的，虽然`class int`确实定义在`builtins.py`。
>
>其次要说的是，也有说法称`int`等为工厂函数，在下以为称之为工厂类更为贴切，而这里namedtuple确确实实是一个工厂函数，用到了工厂模式的思想。但是这不是一个生产对象的工厂，而是一个生产类的工厂, 这个类是tuple的子类，具有tuple的所有特性，并增加了通过属性名元组元素的特性。

### 3. 根据字典中值的大小进行排序

>  字典本身是无序的，即使对字典排序后，可能也无法使用python中字典这种数据结构存储结果

#### 3.1 将字典的各项转换成元组，再对元组排序

> 元组的大小比较类似字符串的比较，逐渐对各个元素比较，如果一个元素能得到结果，则返回比较结果，如果元素相等，则比较各自下一个元素。

1. 产生一个随机的分数表

```python
from random import randint
d = {k:randint(60, 100)for k in 'abcdefg'}
# {'a': 88, 'b': 92, 'c': 91, 'd': 74, 'e': 99, 'f': 82, 'g': 70}
```

2. 将字典转换成元组的列表

```python
l = [(v, k) for k, v in d.items()]
# 也可以使用内置函数zip进行转换
l = list(zip(d.values(), d.keys()))
# [(88, 'a'), (92, 'b'), (91, 'c'), (74, 'd'), (99, 'e'), (82, 'f'), (70, 'g')]
```

3. 使用内置函数sorted进行排序

```python
sorted(l)  # 升序排列
>>> [(70, 'g'), (74, 'd'), (82, 'f'), (88, 'a'), (91, 'c'), (92, 'b'), (99, 'e')]
sorted(l, reverse=True) # 降序排列
>>> [(99, 'e'), (92, 'b'), (91, 'c'), (88, 'a'), (82, 'f'), (74, 'd'), (70, 'g')]
```

#### 3.2 直接对字典的items进行排序

> 字典的items函数，返回包含字典每项(key, value)的可迭代对象，其实每项都是一个元组

```python
>>> d.items() # 此对象并不支持索引
>>> dict_items([('a', 88), ('b', 92), ('c', 91), ('d', 74), ('e', 99), ('f', 82), ('g', 70)])
>>> sorted(d.items(), key=lambda item: item[1])
>>> [('g', 70), ('d', 74), ('f', 82), ('a', 88), ('c', 91), ('b', 92), ('e', 99)]
>>> sorted(d.items(), key=lambda item: item[1], reverse=True)
>>> [('e', 99), ('b', 92), ('c', 91), ('a', 88), ('f', 82), ('d', 74), ('g', 70)]
# 讲结果赋值给p
p = sorted(d.items(), key=lambda item: item[1], reverse=True)
```

#### 3.3 将字典的各项值更新为排名的元组

* 在原字典的基础上更新

```python
for index, (key, value) in enumerate(p, 1):  # 索引从1开始
		d[key] = (index, value)
>>> d
>>> {'a': (4, 88),
 'b': (2, 92),
 'c': (3, 91),
 'd': (6, 74),
 'e': (1, 99),
 'f': (5, 82),
 'g': (7, 70)}
# 虽然字典仍然无序，但是排名和分数高低成正比
```

* 直接生成新的字典

```python
>>> {key: (index, value) for index, (key,value) in enumerate(p, 1)}
>>> {'a': (4, 88),
 'b': (2, 92),
 'c': (3, 91),
 'd': (6, 74),
 'e': (1, 99),
 'f': (5, 82),
 'g': (7, 70)}
```

* 使用OrderedDict存储排序结果

```python
from collections import OrderedDict
od = OrderedDict()
for index, (key, value) in enumerate(p, 1):
  	od[key] = (index, value)
>>> od
>>> OrderedDict([('e', (1, 99)),
             ('b', (2, 92)),
             ('c', (3, 91)),
             ('a', (4, 88)),
             ('f', (5, 82)),
             ('d', (6, 74)),
             ('g', (7, 70))])
```

>为什么不直接将字典转换成有序字典，再直接对有序字典，其实是可以的， 有序字典在于插入顺序有序，并可以通过`move_to_end`等方法调整关键字的顺序，但是无法使用内置函数对有序字典排序，原因主要有sorted排序的原理是传入一个可迭代对象，并根据迭代元素指定比较方式，sorted函数返回一个有序的列表，顺序正是根据指定比较方式得来的顺序，而列表元素则是传入可迭代对象的元素，我们对字典元素进行排序时，一般不会直接传入字典对象，因为迭代字典相等于迭代字典的关键字，也就是`dict.keys()`

### 4. 统计序列中元素的频度

#### 4.1 统计一个随机序列中出现次数最高的3个元素，并统计出现次数

```python
from random import randint 
data = [randint(0, 20) for _ in range(30)] # 必然存在重复元素的序列
>>> data
>>> [2, 9, 11, 16, 1, 20, 11, 0, 5, 18, 7, 13, 15, 18, 7, 7, 11, 10, 14, 19, 1, 6, 8, 16, 19, 13, 17, 7, 4, 11]
```

* 方法一: 将序列转换成字典(序列元素为key，频度为value)，并对字典的值排序

```python
# 快速创建字典
d = dict.fromkeys(data, 0)
# 统计频度
for i in data:
  d[i] += 1
>>> d
>>> {2: 1, 9: 1, 11: 4, 16: 2, 1: 2, 20: 1, 0: 1, 5: 1, 18: 2, 7: 4, 13: 2, 15: 1, 10: 1, 14: 1, 19: 2, 6: 1, 8: 1, 17: 1, 4: 1}

## 对字典进行排序， 并取前三项
>>> sorted(d.items(), key=lambda item: item[1], reverse=True)[:3]
>>> [(11, 4), (7, 4), (16, 2)]
```

>这个方法有一个显然的缺点，我们只需要频度最高的三个元素，但是却对整个字典转换成的列表进行了排序，如果这个列表很大，显然会十分浪费资源。
>
>在一个很大的列表中，只取前几项，很容易想到用堆实现。

```python
# 用堆取列表前三项
import heapq
>>> heapq.nlargest(3, [(v, k) for k, v in d.items()]) # 这里也利用了元组的比较规则
>>> [(11, 4), (7, 4), (16, 2)]
```

* 使用collections模板下Counter对象

```python
from collections import Counter
# Counter的作用如其名，主要用于统计，能将一个序列转换成列表，key为序列元素，value为元素在列表中出现的次数
c = Counter(data)
>>> c
>>> Counter({11: 4, 7: 4, 16: 2, 1: 2, 18: 2, 13: 2, 19: 2, 2: 1, 9: 1, 20: 1, 0: 1, 5: 1, 15: 1, 10: 1, 14: 1, 6: 1, 8: 1, 17: 1, 4: 1})
# Counter是dict的子类，却不是OrderedDict的子类

# 取频度最大的三项
>>> c.most_common(3) # 返回的是一个元素是(key,value)的列表
>>> [(11, 4), (7, 4), (16, 2)]
```

#### 4.2 统计

