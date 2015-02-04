/**
 * Created by xiaowenwang.
 */
public class DList {
    public DListNode head;
    public DListNode tail;
    private int size;

    //construct an empty list
    public DList(){
        head = null;
        tail = null;
        size = 0;
    }

    //test if it's an empty list
    public boolean isEmpty(){ return size == 0;}

    // find size
    public int length(){return size;}

    public void insertFront(int red, int green, int blue, int repeat){
        if(head == null) {
            head = new DListNode(red,green,blue,repeat);
            size++;
            tail = head;

        } else {
            head.prev = new DListNode(red,green,blue,repeat,null,head);
            head = head.prev;
            size++;
        }
    }

    public void insertFront(short red, short green, short blue, int repeat){
        if(head == null) {
            head = new DListNode(red,green,blue,repeat);
            size++;
            tail = head;

        } else {
            head.prev = new DListNode(red,green,blue,repeat,null,head);
            head = head.prev;
            size++;
        }
    }

    public void insertEnd(int red, int green, int blue, int repeat){
        if(tail == null) {
            tail = new DListNode(red,green,blue,repeat);
            size++;
            head = tail;

        } else {
            tail.next = new DListNode(red,green,blue,repeat,tail,null);
            tail = tail.next;
            size++;
        }
    }
    public void insertEnd(short red, short green, short blue, int repeat){
        if(tail == null) {
            tail = new DListNode(red,green,blue,repeat);
            size++;
            head = tail;

        } else {
            tail.next = new DListNode(red,green,blue,repeat,tail,null);
            tail = tail.next;
            size++;
        }
    }

    public DListNode nth(int position) {
        DListNode currentNode;
        if(position < 1 || head == null){
            return null;
        } else {
            currentNode = head;
            while(position > 0) {
                currentNode = currentNode.next;
                if(currentNode == null) {
                    return null;
                }
                position--;
            }
        return currentNode;
        }
    }

    public int getSize(){
        return this.size;
    }

    public void deleteNode(DListNode n){
        if(n.prev == null) {
            head = n.next;
            n.next.prev = null;
        } else if (n.next == null) {
            tail = n.prev;
            n.prev = null;
        } else {
            n.prev.next = n.next;
            n.next.prev = n.prev;
        }
        size--;
    }

    public void insertAfter(short red, short green, short blue, int repeat, DListNode cur) {
        if(cur.next != null) {
            DListNode node = new DListNode(red, green, blue, repeat, cur, cur.next);
            cur.next= node;
            node.next.prev = node;
            size++;

        } else {
            DListNode node = new DListNode(red, green, blue, repeat, cur, cur.next);
            cur.next = node;
            tail = node;
            size++;
        }
    }

    public void insertBefore(short red,short green,short blue,int repeat, DListNode cur) {
        if(cur.prev == null) {
            DListNode node = new DListNode(red,green,blue,repeat,cur.prev,cur);
            cur.prev = node;
            head = node;
            size++;
        } else{
                DListNode node = new DListNode(red,green,blue,repeat,cur.prev,cur);
                cur.prev = node;
                node.prev.next = node;
                size++;
        }
    }

}
