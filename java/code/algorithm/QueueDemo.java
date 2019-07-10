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