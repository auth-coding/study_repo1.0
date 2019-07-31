# 队列

### 1 应用场景

队列，即queue是常用数据结构之一， 是对现实中的队列良好的抽象，是对排队，异步处理等业务场景的真实还原，采用FIFO(first in first out)的原则。



### 2 队列的定义

队列是线性结构中的一种，是一种受限的线性表，通常特性有只能从队首拿元素，从队尾插入元素。

队列的分类(非严格分类)
1. 按逻辑结构划分:
    * 单队列
    * 循环队列
    * 双端队列
2. 按存储结构划分：
    * 数组模仿队列
    * 链队

### 3 原理分析

1. 数组模仿队列
数组是一块连续的地址空间，且大小固定，则队列需要提前设置队列的maxSize， 并且做好一些列约定。

* 队列分别有一个队首指针front和队尾指针rear；
* 初始化时 rear = front = -1， 即队列为空，队尾指针和队首指针均不指向任何值。
* 添加队尾元素时，使用++rear作为数组下标，即队尾指针始终指向队尾元素
* 移除队首元素时， 同样使用++front作为数组下标，即队首指针指向的是队首元素之前，也就是当前队首指针没有指向任何队列元素，这样处理的原因是因为约定队列初始化为空时rear = front = -1，并且增加队尾元素时不应该移动队首指针。
* 判读队列是否为空， 则判断 rear指针和front指针是否相等
* 判断队列是否已满，则判断 rear指针是否等于maxSize

2. 数组模拟循环队列
循环队列也称为环形队列，同样使用数组模拟，同样拥有maxSize这个属性或者限制。
在数组模仿队列中，在添加队尾元素和拿取队首元素的操作中， rear和front都只会增加，而不会减少，所以在循环队列中，通过对rear和front取模的方式，将队尾指针和队首指针限定在数组索引内，以达到数组空间的复用。
同时需要注意的是， 链队列不存在空间限定和复用的问题，也就不用引入循环队列。
为了通过数组模拟循环队列，同样做了以下规定，相较于数组模拟队列有一定程度的改变：
* 数组初始化时，`rear =front =0`，队列为空；
* 队列增加队尾元素时，队尾元素添加到当前索引位置，添加后队尾元素的下标位置为`rear=(rear+1+maxSize)/maxSize`，即当前队尾指针指向的是队尾元素的前一个数组空间，同时因为规定队首指针和队尾指针相等时，队列为空，所以队尾指针指向的空间始终没有数据;
* 队列拿取队首元素时，即拿取arr[front]，出队后队首元素的下标为`front=(front+maxSize+1)/maxSize`；
* 判断队列为空时， rear=front；
* 队列满时 ，队尾指针和队首指针相邻`(front-rear)/maxSize==1`,但此时队尾指针指向的数组空间并没有元素，即指针rear指向的数组空间arr[rear]是始终没有元素， 即队列数组大小为maxSize， 但是有效数据个数位maxSize-1。

### 4 代码实现
1. 数组模拟队列的实现
```java
class QueueDemo{
    public static void main(String[] args) {
    }
}

class ArrayQueue{
    private int maxSize;
    private int rear; // 队尾指针
    private int front; // 队首指针
    private int array[]; // 数组模仿队列空间

    public void ArrayQueue(int size){
        // 初始化一个空队列
        this.maxSize = size;
        this.rear = this.front = -1;
        this.array = new int[maxSize];
    }

    public boolean isEmpty(){
        if (this.rear == this.front){
            return true;
        }else{
            return false;
        }
    }

    public boolean isFull(){
        if (this.rear == maxSize){
            return true;
        }else{
            return false;
        }
    }

    public void add(int e){
        // 在队尾添加一个元素
        if (this.isFull()){
            throw new RuntimeException("队列已满， 无法添加元素");
        }else{
            this.array[++this.rear] = e;
        }
    }

    public int remove(){
        // 移除队首元素
        if (this.isEmpty()){
            throw new RuntimeException("队列为空，无法删除元素");
        }else{
           return this.array[++this.front];
        }
    }

    public void showQueue(){
        if (this.isEmpty()){
            System.out.println("队列为空");
        }
        else{
            int p = this.front;
            while (p != this.rear){
                System.out.println(this.array[++p]);
            }
        }
    }

}
```
2. 数组模拟循环队列代码实现
```java
class CircleArrayQueueDemo {
    public static void main(String[] args) {
        
    }
}

class CircleArray{
    public int maxSize;
    public int rear;
    public int front;
    public int[] arr;
    public CircleArray(int arrMaxSize){
        this.maxSize = arrMaxSize;
        this.rear = this.front = 0;
        this.arr = new int[this.maxSize];
    }
    public boolean isEmpty(){
        return this.rear == this.front;
    }

    public boolean isFull(){
        return (this.rear+1)%this.maxSize == this.front;
    }

    public int getSize(){
        return (rear-front+maxSize)%maxSize;
    }

    public void add(int e){
        if (this.isFull()){
            System.out.println("队列满， 不能加入数据");
        }else{
            this.arr[rear] = e;
            rear = (rear+1)/maxSize;
        }
    }
    public int get(){
        if(this.isEmpty()){
            // System.out.println("队列空，不能取数据");
            // 因为该方法必须要返回int， 所以队列为空时通过抛出异常
            // 而不至于返回可能的队列元素
            throw new RuntimeException("队列空，不能取数据");
        }else{
            // front指向队列第一个元素
            //  保存临时变量
            int tmp = this.arr[front];
            // 改变front
            front = (front+1)%maxSize;
            return tmp;
        }
    }
}
```

### 5 总结

使用数组模拟队列的方式，有以下优缺点。
优点：
* 逻辑清楚，实现简单
缺点：
* 数组大小固定；
* 每个数组空间仅能使用一次。

使用数组模拟循环数组的优缺点
优点：
*  数组空间可复用
缺点：
* 逻辑较为复杂，需想清楚指针与元素间的对应关系，且实际有效数据为maxSize-1

