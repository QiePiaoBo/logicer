package com.dylan.blog;

import org.checkerframework.checker.units.qual.C;
import org.junit.Test;

import java.util.*;

public class CommonTest {

    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    @Test
    public void listNodeTest(){
        // 图书整理
        reverseBookList(new ListNode(1));
    }


    public ListNode trainingPlan3(ListNode head, int cnt) {
        ListNode cur = head;
        List<ListNode> list = new ArrayList<>();
        while (null != cur){
            list.add(cur);
            cur = cur.next;
        }
        if (list.size() == 0 || list.size() < cnt){
            return null;
        }
        return list.get(list.size() - cnt);
    }

    public Node copyRandomList(Node head) {
        if (null == head){
            return head;
        }
        Node cur = head;
        // key为了避免不同节点有相同值的情况所以不能使用节点的数值，value因为要存储next的指向也不能
        Map<Node, Node> nodeMap = new HashMap<>();
        while (null != cur){
            // 在第一次遍历时就把新的节点完成复制
            nodeMap.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        // cur重新指向头节点以便再次进行循环
        cur = head;
        while (null != cur){
            // 将旧链表的next所对应的新链表节点赋值给新链表的节点的next属性
            nodeMap.get(cur).next = nodeMap.get(cur.next);
            // 将旧链表的random所对应的新链表节点赋值给新链表的节点的random属性
            nodeMap.get(cur).random = nodeMap.get(cur.random);
            // 新链表往后一项
            cur = cur.next;
        }
        // 根据旧链表头获取新链表头并返回
        return nodeMap.get(head);
    }


    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Map<Integer, Integer> map = new HashMap<>();
        // 先遍历A链表 将所有的值存储到map中 用于判断B中元素是否存在于A中
        while (null != headA){
            map.put(headA.val, 1);
            headA = headA.next;
        }
        // 遍历B链表 如果headB的值存在于map中 说明此节点存在于链表A中 直接返回此节点
        while (null != headB){
            if (map.containsKey(headB.val)){
                return headB;
            }
            headB = headB.next;
        }
        // 如果B中所有元素都不存在于map中 说明AB没有相同的节点
        return null;
    }


    public ListNode trainningPlan3(ListNode l1, ListNode l2) {
        // 首先定义一个虚拟头 开启一个总链表用于汇总两个链表的结果
        ListNode newHead = new ListNode(0);
        // 定义一个指针用于遍历总链表
        ListNode cur = newHead;
        // 两个链表同时遍历 当有一个遍历完之后 说明剩下的那个链表中所有元素都是大的
        while (null != l1 && null != l2){
            // 比较两个链表链表当前元素的值
            if (l1.val < l2.val){
                // 如果1的值小 说明1这个元素应该添加到总链表中并往后移一项
                cur.next = l1;
                l1 = l1.next;
            }else {
                // 如果2的值小 说明1这个元素应该添加到总链表中并往后移一项
                cur.next = l2;
                l2 = l2.next;
            }
            // 总链表的指针移到下一项用于存储下一个元素
            cur = cur.next;
        }
        // 将剩下的链表中所有元素直接添加到总链表中
        cur.next = l1 != null ? l1 : l2;
        // 虚拟头的下一项是应该返回的真实总链表的头
        return newHead.next;
    }

    public ListNode trainingPlan2(ListNode head, int cnt) {
        ListNode cur = head;
        List<ListNode> list = new ArrayList<>();
        // 遍历链表 将所有节点按照顺序添加到列表中
        while (null != cur){
            list.add(cur);
            cur = cur.next;
        }
        // 处理极端条件
        if (list.size() == 0 || list.size() < cnt){
            return null;
        }
        // 计算倒数第n个节点是正数第几个节点（正数从0开始）
        return list.get(list.size() - cnt);
    }


    public ListNode trainingPlan1(ListNode head) {
        if (null == head || null == head.next){
            return head;
        }
        ListNode cur = head;
        ListNode newNext = null;
        ListNode temp;
        while (null != cur){
            temp = cur.next;
            cur.next = newNext;
            newNext = cur;
            cur = temp;
        }
        return newNext;
    }

    public ListNode deleteNode(ListNode head, int val) {

        // 如果头节点需要删除， 直接返回第二个节点即可
        if (head.val == val){
            return head.next;
        }
        // 遍历链表，从第二个元素开始
        ListNode cur = head;
        ListNode temp;
        while (null != cur.next){
            // 判断该元素非空而且是需要删除的元素
            if(cur.next.val == val){
                // 将当前元素指向下一个元素的下一项并将待删除的元素的下一项指向null
                temp = cur.next;
                cur.next = temp.next;
                temp.next = null;
                break;
            }
            cur = cur.next;
        }
        return head;
    }




    /**
     * 图书整理
     * @param head
     * @return
     */
    public int[] reverseBookList(ListNode head) {
        // 新建LinkedList作为暂存栈使用
        LinkedList<Integer> stack = new LinkedList<>();
        // 新建临时节点用于计算，保留原head
        ListNode cur = head;
        while(null != cur){
            // 将临时节点的每个值都添加到栈中
            stack.addLast(cur.val);
            // 添加完后临时节点指向下一个节点
            cur = cur.next;
            // 临时节点指到链表的最后一个节点时 该节点的下一个节点为空 需要退出循环
        }
        // 根据栈的长度新建结果数组
        int[] res = new int[stack.size()];
        // 以数组长度而不是栈的长度为依据构建循环，元素出栈并为数组的每个位置赋值
        for(int i = 0; i < res.length; i ++){
            res[i] = stack.removeLast();
        }
        // 返回结果数组
        return res;
    }

}
