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