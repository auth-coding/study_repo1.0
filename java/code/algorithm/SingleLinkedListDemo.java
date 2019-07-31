public class SingleLinkedListDemo{
    public static void main(String[] args){
        // 测试
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        // 创建链表
        SingleLinkedList list = new SingleLinkedList();
        list.add(hero1);
        list.add(hero2);
        list.add(hero3);
        list.add(hero4);
        list.show();
        
    }
}

class SingleLinkedList{
    public HeroNode head = new HeroNode(0, "", "");

    public void add(HeroNode node){
        HeroNode tmp = head;
        // 遍历链表
        while(tmp.next != null){
            // 指针往后移动一个节点
            tmp = tmp.next;
        }
        // 退出循环时， tmp指向最后一个节点
        tmp.next = node ;
    }

    public void show(){
        /**
         * 显示链表
         */
        // 判断链表是否为空
        if(head.next==null){
            System.out.println("链表为空");
            return;
        }
        HeroNode tmp = head.next;
        while(tmp != null){
            System.out.println(tmp);
            tmp = tmp.next;
        }
    }
}

class HeroNode{
    public int no; //编号
    public String name;
    public String nickname;
    public HeroNode next; // 指向下一个节点

    public HeroNode(int no, String name, String nickname){
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    //重写toString
    // @override
    public String toString(){
        return "HeroNode [no=" + this.no +", name=" + this.name + ", nickname=" + this.nickname + "]";
    }
}