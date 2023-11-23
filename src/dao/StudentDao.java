package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import dto.StudentDto;
import file.FileIO;

// Data Access Object : 데이터 취급하는 클래스
public class StudentDao {
	
	Scanner sc = new Scanner(System.in);
	
	// 학생 데이터 관리 배열
	private StudentDto student[];
	
	private int count;
	
	private FileIO fio;
	
	// 추가 삭제 검색 수정(CRUD)
	public StudentDao() {
		fio = new FileIO("student");
		fio.create();
		
		count = 0;
		
		student = new StudentDto[10];  // 변수만 생성
		// StudentDto student1, student2, ...., student10
		
		// dto를 생성
		//for (int i = 0; i < student.length; i++) {
		//	student[i] = new StudentDto();
		//}
	}
	
	public void insert() {
		System.out.println("학생 정보 입력입니다");

		System.out.print("이름 >> ");
		String name = sc.next();

		System.out.print("나이 >> ");
		int age = sc.nextInt();

		System.out.print("신장 >> ");
		double height = sc.nextDouble();

		System.out.print("주소 >> ");
		String address = sc.next();

		System.out.print("국어 >> ");
		int kor = sc.nextInt();

		System.out.print("영어 >> ");
		int eng = sc.nextInt();

		System.out.print("수학 >> ");
		int math = sc.nextInt();

		student[count] = new StudentDto(name, age, height, address, kor, eng, math);
		count++;
	}
	
	public void delete() {
		// 이름입력
		System.out.print("삭제하고 싶은 학생의 이름 >> ");
		String name = sc.next();
		
		// 검색
		int index = search(name, student);
		
		
		if(index != -1) {	// 찾았음
			student[index] = null;
			
			System.out.println("정상적으로 삭제되었습니다");
		}else {
			System.out.println("학생명단에 없습니다");
		}		
		
		// name = ""	<- string
		// age = 0		<- int
	}
	
	public void select() {
		// 이름입력
		System.out.print("검색하고 싶은 학생의 이름 >> ");
		String name = sc.next();
		
		for (int i = 0; i < student.length; i++) {
			StudentDto dto = student[i];
			if (dto != null && dto.getName().equals("") == false) {
				if(name.equals(dto.getName())) {
					dto.print();
				}
			}
		}
		// 내방법
		// int index = search(name, student);
		// 
		// if(index != -1) {
		// 	System.out.println("데이터를 찾았습니다");
		// 	System.out.println(student[index].toString());
		// }else {
		// 	System.out.println("학생명단에 없습니다");
		// }		
	}
	
	public void update() {
		// 이름입력
		System.out.print("수정하고 싶은 학생의 이름 >> ");
		String name = sc.next();
		
		int index = search(name, student);
		 
		if(index == -1) {
			System.out.println("학생명단에 없습니다");
			return;
		}
		
		// 국어, 영어, 수학 점수 수정
		System.out.println("수정할 데이터를 찾았습니다");
		
		System.out.print("국어 >> ");
		int kor = sc.nextInt();
		
		System.out.print("영어 >> ");
		int eng = sc.nextInt();
		
		System.out.print("수학 >> ");
		int math = sc.nextInt();
		
		student[index].setKor(kor);
		student[index].setEng(eng);
		student[index].setMath(math);
		
		System.out.println("수정을 완료했습니다");
	}
	
	public void allData() {
		for (int i = 0; i < student.length; i++) {
			StudentDto dto = student[i];
			if (dto != null) {
				System.out.println(dto.toString());
			}
		}
	}
	
	static int search(String name, StudentDto[] student) {
		int index = -1;
		for (int i = 0; i < student.length; i++) {
			StudentDto dto = student[i];
			
			if(dto != null) {
				if(name.equals(dto.getName())) {
					index = i;
					break;
				}
			}
		}
		return index;
	}
	
	public void save() {
		
		// 실제로 삭제된 데이터를 제외한 (정상적인)데이터가 몇개?
		int ci = 0;
		for (int i = 0; i < student.length; i++) {
			if(student[i]!= null
					&& student[i].getName().equals("") == false) {
				ci++;
			}
		}
			
		// 배열
		String arr[] = new String[ci];
		int j = 0;
		for (int i1 = 0; i1 < student.length; i1++) {
			if(student[i1] != null
					&& student[i1].getName().equals("") == false) {
				arr[j] = student[i1].toString();
				j++;
			}
		}
		fio.dataSave(arr);
		
	}

	public void load() {
		String arr[] = fio.dataLoad();
		
		if(arr == null || arr.length == 0) {
			count = 0;
			return;
		}
		
		// (추가될) 다음 데이터의 index
		count = arr.length;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] == "" || arr[i].equals("")) {
				continue;
			}
			String[] split = arr[i].split("-");
			String name = split[0];
			int age = Integer.parseInt(split[1]);
			double height = Double.parseDouble(split[2]);
			String address = split[3];
			int kor = Integer.parseInt(split[4]);
			int eng = Integer.parseInt(split[5]);
			int math = Integer.parseInt(split[6]);
			
			student[i] = new StudentDto(name, age, height, address, kor, eng, math);
		}
		System.out.println("데이터 로드 성공!");
		
	}
	
	
	
}
