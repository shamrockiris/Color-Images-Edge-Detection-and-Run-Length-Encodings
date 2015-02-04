/**
 * Created by xiaowenwang.
 */
public class DListNode {
    int red;
    int green;
    int blue;
    int repeat;
    DListNode next;
    DListNode prev;

    public DListNode(int red, int green, int blue, int repeat, DListNode prev,DListNode next){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.repeat = repeat;
        this.next = next;
        this.prev = prev;
    }

    public DListNode(short red, short green, short blue, int repeat, DListNode prev,DListNode next){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.repeat = repeat;
        this.next = next;
        this.prev = prev;
    }


    public DListNode(int red, int green, int blue, int repeat){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.repeat = repeat;
        this.next = null;
        this.prev = null;
    }

    public DListNode(short red, short green, short blue, int repeat){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.repeat = repeat;
        this.next = null;
        this.prev = null;
    }

    public DListNode copy(DListNode n){
        this.red = n.red;
        this.green = n.green;
        this.blue = n.blue;
        this.repeat = n.repeat;
        this.next = n.next;
        this.prev = n.prev;
        return this;
    }

    public void setNode(int red,int green,int blue,int repeat){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.repeat = repeat;
    }
}
