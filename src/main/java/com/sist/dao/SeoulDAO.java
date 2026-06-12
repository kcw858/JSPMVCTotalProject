package com.sist.dao;
import java.util.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sist.vo.SeoulVO;

import java.io.*;

public class SeoulDAO {
	private static SqlSessionFactory ssf;
	static
	{
		try
		{
			Reader reader = Resources.getResourceAsReader("Config.xml");
			ssf = new SqlSessionFactoryBuilder().build(reader);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	//기능
	/*
	 * <select id="seoulListData" resultType="SeoulVO" parameterType="hashmap">
		SELECT no,title,poster 
		FROM ${table}
		ORDER BY no ASC
		OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY
		</select>
		<select id="seoulTotalPage" resultType="int" parameterType="hashmap">
			SELECT CEIL(COUNT(*)/12.0)
			FROM ${table}
		</select>
	 */
	public static List<SeoulVO> seoulListData(Map map)
	{
		SqlSession session = ssf.openSession();
		List<SeoulVO> list = session.selectList("seoulListData",map);
		session.close();
		return list;
	}
	
	/*
	 * resultType="int"  ==> return형
	 * parameterType="hashmap" ==> 매개변수
	 */
	public static int seoulTotalPage(Map map)
	{
		SqlSession session = ssf.openSession();
		int total = session.selectOne("seoulTotalPage",map);
		session.close();
		return total;
	}
	
	/*
	 <update id="hitIncreMent" parameterType="hashmap">
		UPDATE ${table} SET
		hit=hit+1
		WHERE no=#{no}
	</update>
	<select id="seoulDetailData" resultType="SeoulVO" parameterType="hashmap">
		SELECT * FROM ${table}
		WHERE no=#{no}
	</select>
	 */
	
	//selectList / selectOne 
	//			   --------- 상세보기
	//--------- 목록, 검색
	//DML : update / delete / insert
	/*
	 * 데이터 읽기 :select
	 * 데이터 갱신: insert / update / delete ==> 반드시 commit을 해주어야 한다
	 * 
	 * => commit
	 * 
	 * SqlSession session = ssf.openSession(); ==> autocommit false
	 * SqlSession session = ssf.openSession(true); ==> autocommit true
	 */
	public static SeoulVO seoulDetailData(Map map)
	{
		SqlSession session = ssf.openSession(true);
		String t = (String)map.get("table");
		if(!t.endsWith("hotel"))
			session.update("hitIncreMent",map);
		//session.commit(); //트랜잭션 (일괄처리)
		// => catch ==> session.rollback() 가능
		SeoulVO vo = session.selectOne("seoulDetailData",map);
		session.close();
		return vo;
	}
}
