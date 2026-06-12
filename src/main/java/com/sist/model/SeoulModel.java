package com.sist.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sist.controller.Controller;
//브라우저로 전송 ==> Model
//Model: 1.요청받기 2. 요청처리(DAO,OPenAPI) 3. 결과값 : session,request에 담아서 JSP로 전송
/*
 * 데이터 전송
 * => request.setAttribute() / session.setAttribute()
 * 		---------------			-----------------
 * 		대부분					사용자 정보 저장 , 장바구니 저장, 예약 정보..
 * 		한개의 JSP에서만 사용이 가능	프로그램 종료시까지 기억
 * 								모든 JSP에서 사용이 가능
 * 
 */
import com.sist.controller.RequestMapping;
import com.sist.dao.SeoulDAO;
import com.sist.vo.SeoulVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Controller 
public class SeoulModel {
	private String[] table = {
		"",
		"seoul_location",
		"seoul_nature",
		"seoul_shop",
		"seoul_hotel"
	};
	
	private String[] title = {
			"",
			"서울 명소",
			"서울 자연",
			"서울 쇼핑",
			"서울 호텔"
		};
	
	@RequestMapping("seoul/list.do")
	public String seoul_list(HttpServletRequest request,HttpServletResponse response)
	{
		String page = request.getParameter("page");
		if(page == null)
			page="1";
		
		String tno = request.getParameter("tno");
		if(tno == null)
			tno="1";
		
		//현재 페이지
		int curpage = Integer.parseInt(page);
		
		//오라클에서 값을 가지고 온다
		//FROM ${table} ==> seoul_location은 테이블명이라서 문자열인 따옴표가 없어야한다 ${}
		//OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table", table[Integer.parseInt(tno)]); //Key명이 동일해야한다
		map.put("start", (curpage*12)-12);
		
		List<SeoulVO> list = SeoulDAO.seoulListData(map);
		int totalpage = SeoulDAO.seoulTotalPage(map);

		//데이터를 전송 (출력 대상)
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("tno", tno);
		request.setAttribute("title", title[Integer.parseInt(tno)]);
		
		return "../seoul/list.jsp"; // request를 받는 JSP 지정
	}
	
	//어노테이션 => 구분자(인덱스 => 빠르게 찾기)
	//어노테이션은 밑에 있거나 옆에 있는 메소드/클래스/멤버변수 처리
	@RequestMapping("seoul/detail.do") //조건문 대신 사용 / 메소드 구분자
	public String seoul_detail(HttpServletRequest request,HttpServletResponse response)
	{
		String no = request.getParameter("no");
		String tno = request.getParameter("tno");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", Integer.parseInt(no));
		map.put("table", table[Integer.parseInt(tno)]);
		
		SeoulVO vo = SeoulDAO.seoulDetailData(map);
		
		
		request.setAttribute("vo", vo);
		request.setAttribute("tno", tno);
		
		return "../seoul/detail.jsp";
	}
	
}
