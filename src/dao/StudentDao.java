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

// Data Access Object : 데이터 취급하는 클래스
public class StudentDao {
	
	Scanner sc = new Scanner(System.in);
	
	// 학생 데이터 관리 배열
	private StudentDto student[];
	
	private int count;
	
	// 추가 삭제 검색 수정(CRUD)
	public StudentDao() {
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
		
		File f = new File("c:\\tmp\\studentDto.txt");
		
		String strLine[] = new String[student.length];
		
		for (int i = 0; i < student.length; i++) {
			StudentDto dto = student[i];
			if(dto != null) {
				strLine[i] = dto.toString();
			}else {
				strLine[i] = "";
			}
		}
		
		try {
			PrintWriter pw  = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			
			for (String s : strLine) {
				if(s != null && !s.equals("")) {
					pw.println(s);
				}
			}
			
			pw.close();
			
		} catch (IOException e) {
			System.out.println("파일에 저장되지 않았습니다");
			return;
		}
		
		System.out.println("정상적으로 저장되었습니다.");
	}

	public void load() {
		File f = new File("c:\\tmp\\studentDto.txt");
		
		String strLine[] = new String[student.length];
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			String str = "";
			int count = 0;
			while ( (str = br.readLine()) != null ) {
				System.out.println(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
