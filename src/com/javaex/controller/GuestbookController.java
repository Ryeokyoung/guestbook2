package com.javaex.controller;

public class GuestbookController {

	//필드
		private static final long serialVersionUID = 1L;
	       
	    //생성자-디폴트

		
		
		//get 방식
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			//post 방식일때 한글 깨짐 방지
			request.setCharacterEncoding("UTF-8");
			//uf8로 인코딩 세팅해놔라 
			
			//action 파라미터 꺼내기
			String action = request.getParameter("action");      //http요청 : 우리가 크롬을통해 검색을 하여 네이버 사이트를 인입할 때, 크롬이 사이트를 보여달라 요청하는 것 
			System.out.println(action);                          //파라미터 어떤 함수나 그러한 것이 동작할 때 필요한 값을 의미 
																//
																//ex) 로그인절차를 수행하기 위한 두개의 파라미터(아이디,비밀번호)를 받음 
																//get parameter() (파라미터 중 하나/키)값을 불러오는 행위 
			
			if("list".equals(action)) { //리스트					// "".equals() "string"형에서 나온 메소드 (형태는 보장되어 있어서 값만 체크해주면 됨) 
																// 	string형 보장,  "list"라는 문자가 action에 해당하는 문자와 동일한지 여부 확인 
				//데이터 가져오기
				GuestBookDao gbDao = new GuestBookDao();
				List<GuestBookVo> gbList = gbDao.getGuestList();			//겟게스트 리스트 함수호출후 리턴타입 List<GuestBookVo>       // 변수 : 내가 원하는 값을 담을 수 있는 공간 
																			//list~ ~list 비교 겟게스트 리스트는 위 인스턴스의 "메소드이름"  //자바의 룰 변수타입 변수이름 = 변수이름에 집어넣을 값 
				//request에 데이터 추가												
																			//ex> public int aa(int a) {
				request.setAttribute("gbList", gbList);						//return a + 10; 
				//request.setAttribute("객체명", 객체 ) ; 						//}	 return (int a) + 10 = aa + 10 > 자료형은 int(리턴타입)
				//jsp간 객체이동을 위해 사용하는 함수 
				
				
				//데이터 + html -> jsp
				WebUtil.forward(request, response, "/WEB-INF/addList.jsp");
				
			} else if("write".equals(action)) { //글 추가하기
				//파라미터 가져오기
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				String content = request.getParameter("content");
				String date = request.getParameter("reg_date");
				
				//guestInsert로 DB에 추가
				GuestBookDao gbDao = new GuestBookDao();
				int count  = gbDao.guestInsert(new GuestBookVo(name, password, content, date));
				System.out.println(count);
				
				//리다이렉트 list
				WebUtil.redirect(request, response, "./gbc?action=list");
				
			} else if("deleteForm".equals(action)) {
				
				WebUtil.forward(request, response, "/WEB-INF/deleteForm.jsp");
				
				
			} else if("deleteForm".equals(action)) {
				System.out.println("ㅇㅅㅇ");
			}
			
			
			else if("delete".equals(action)) {
				//파라미터 가져오기
				int delNo = Integer.parseInt(request.getParameter("del_no"));
				String delPw = request.getParameter("del_pw");
				
				
				GuestBookDao gbDao = new GuestBookDao();
				
				gbDao.guestDelete(delNo, delPw);
				
				//리다이렉트 list
				WebUtil.redirect(request, response, "/guestbook2/gbc?action=list");
				
				
			} else {
				System.out.println("action 파라미터 없음");
				WebUtil.redirect(request, response, "/guestbook2/gbc?action=list");
			}
			
			
		}

		
		
		
		//post 방식
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
		}

	}
}
