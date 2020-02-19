package com.atguigu.hashtab;

import java.util.Scanner;

/**
 * @Description
 * @author liZongXiao
 * @version
 * @date 2020年2月19日下午4:32:40
 * 哈希表管理雇员信息系统
 */
public class HashTabDemo {
	public static void main(String[] args) {
		//创建哈希表
		HashTab hashTab = new HashTab(7);
		Emp emp;
		String key = "";
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("add:  添加雇员");
			System.out.println("list: 显示雇员");
			System.out.println("find: 查找雇员");
			System.out.println("exit: 退出系统");
			key = scanner.next();
			switch (key) {
			case "a":
				System.out.println("输入id");
				int id = scanner.nextInt();
				System.out.println("输入名字");
				String name = scanner.next();
				emp = new Emp(id, name);
				hashTab.add(emp);
				break;
			case "l":
				hashTab.list();
				break;
			case "f":
				System.out.println("请输入要查找的id");
				id = scanner.nextInt();
				hashTab.findEmpById(id);
				break;
			case "e":
				scanner.close();
				System.exit(0);
				break;
			default:
				break;
			}
		}
	}
}

//管理多条链表
class HashTab {
	private EmpLinkedList[] empLinkedListArray;
	private int size; //表示有多少条链表
	public HashTab(int size) {
		super();
		this.size = size;
		empLinkedListArray = new EmpLinkedList[size];
		//初始化每个链表
		for (int i = 0; i < size; i++) {
			empLinkedListArray[i] = new EmpLinkedList();
		}
	}
	/*
	 * 编写散列函数, 使用一个简单取模法
	 */
	public int hashFun(int id) {
		return id % size;
	}
	/*
	 * 添加雇员
	 */
	public void add(Emp emp) {
		int empLinkedListNO = hashFun(emp.id);
		empLinkedListArray[empLinkedListNO].add(emp);
	}
	/*
	 * 遍历所有的链表,遍历hashtab
	 */
	public void list() {
		for (int i = 0; i < size; i++) {
			empLinkedListArray[i].list(i);
		}
	}
	/*
	 * 根据输入的id,查找雇员
	 */
	public void findEmpById(int id) {	
		int empLinkedListNO = hashFun(id);
		Emp emp = empLinkedListArray[empLinkedListNO].findEmpById(id);
		if (emp != null) {
			System.out.printf("在第%d条链表中找到 雇员 id = %d\n", (empLinkedListNO + 1), id);
		}else{
			System.out.println("在哈希表中，没有找到该雇员~");
		}
	}
}

//表示链表
class EmpLinkedList {
	//头指针，执行第一个Emp,默认null
	private Emp head;
	/*
	 * 添加雇员到链表
	 * 假定，当添加雇员时，id 是自增长，即id的分配总是从小到大，因此我们将该雇员直接加入到本链表的最后即可
	 */
	public void add(Emp emp) {
		if (head == null) {
			head = emp;
			return;
		}
		//如果不是第一个雇员，则使用一个辅助的指针，帮助定位到最后
		Emp curEmp = head;
		while (true) {
			if (curEmp.next == null) {
				break;
			}
			curEmp = curEmp.next;
		}
		curEmp.next = emp;
	}
	/*
	 * 遍历链表的雇员信息
	 */
	public void list(int no) {
		if (head == null) {
			System.out.println("第 "+(no+1)+" 链表为空");
			return;
		}
		System.out.print("第 "+(no+1)+" 链表的信息为");
		Emp curEmp = head;
		while (true) {
			System.out.printf(" => id = %d name = %s\t", curEmp.id, curEmp.name);
			if (curEmp.next == null) {
				break;
			}
			curEmp = curEmp.next;
		}
		System.out.println();
	}
	/*
	 * 根据id查找雇员
	 */
	public Emp findEmpById(int id) {
		if(head == null) {
			System.out.println("链表为空");
			return null;
		}
		Emp curEmp = head;
		while (true) {
			if (curEmp.id == id) {
				break;//这时curEmp就指向要查找的雇员
			}
			if (curEmp.next == null) {
				curEmp = null;
				break;
			}
			curEmp = curEmp.next;
		}
		return curEmp;
	}
}

//表示一个雇员
class Emp {
	public int id;
	public String name;
	public Emp next; //next 默认为 null
	public Emp(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
