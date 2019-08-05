public class SingleLinkedListDemo {
    public static void main(String[] args) {
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
        // 测试有序插入链表
        HeroNode hero6 = new HeroNode(6, "鲁智深", "花和尚");
        HeroNode hero5 = new HeroNode(5, "武松", "行者");
        System.out.println("——————————————————————————————乱序插入后——————————————————");
        list.sortedAdd(hero6);
        list.sortedAdd(hero5);
        list.show();

        // 测试更新节点
        HeroNode newHero4 = new HeroNode(6, "公孙胜", "入云龙");
        list.update(newHero4);
        System.out.println("——————————————————————————————更新节点后——————————————————————————");
        list.show();

        // 测试删除节点
       
        HeroNode removed_node = list.remove(3);
        System.out.println(removed_node);
        System.out.println("删除3号节点后");
        list.show();
        System.out.printf("现在链表长度为%d\n", list.getLength());

    }
}

class SingleLinkedList {
    private HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return this.head;
    }

    public void add(HeroNode node) {
        HeroNode tmp = this.head;
        // 遍历链表
        while (tmp.next != null) {
            // 指针往后移动一个节点
            tmp = tmp.next;
        }
        // 退出循环时， tmp指向最后一个节点
        tmp.next = node;
    }

    public void sortedAdd(HeroNode node) {
        /**
         * 按照英雄no从小到大顺序插入
         */
        HeroNode tmp = this.head;
        while (true) {
            if (tmp.next != null) {
                // no不允许重复，不用比较等于的情况
                if (tmp.next.no > node.no) {
                    node.next = tmp.next;
                    tmp.next = node;
                    break;
                }
                tmp = tmp.next;
            } else {
                tmp.next = node;
                node.next = null;
                break;
            }
        }

    }

    public void update(HeroNode node) {
        /**
         * 根据英雄编号更新 编号不存在给出错误提示
         */
        HeroNode tmp = this.head;
        while (tmp.next != null) {
            if (tmp.next.no == node.no) {
                node.next = tmp.next.next;
                tmp.next = node;
                return;
            }
            tmp = tmp.next;
        }
        System.out.println("未找到对应节点");
    }

    public HeroNode remove(int no) {
        HeroNode tmp = this.head;
        while(tmp.next != null){
            if(tmp.next.no == no){
                HeroNode res = tmp.next;
                tmp.next = tmp.next.next;
                return res;
            }
            tmp = tmp.next;
        }
        System.out.println("没有找到对应节点");
        return null;
    }

    public void show() {
        /**
         * 显示链表
         */
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode tmp = head.next;
        while (tmp != null) {
            System.out.println(tmp);
            tmp = tmp.next;
        }
    }

    public int getLength(){
        int length = 0;
        HeroNode tmp = this.head;
        while(tmp.next != null){
            length++ ;
            tmp = tmp.next;
        }
        return length;
    }
}

class HeroNode {
    public int no; // 编号
    public String name;
    public String nickname;
    public HeroNode next; // 指向下一个节点

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    // 重写toString
    // @override
    public String toString() {
        return "HeroNode [no=" + this.no + ", name=" + this.name + ", nickname=" + this.nickname + "]";
    }
}