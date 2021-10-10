package com.psdwsb.hellodemo.repository;

import com.psdwsb.hellodemo.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    /*@Autowired*/
    // 생성자가 하나만 있는 경우, Spring bean으로 등록된 경우 Autowired 생략 가능
    // 두 개 이상의 생성자가 있는 경우는 생략 불가
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        // dataSource 넣어주는 게 Spring에서 권장하는 방법
        // ** Template: Design pattern 중 Template method pattern 적용
    }

    @Override
    public Member save(Member member) {
        // ** insert 문을 별도로 작성하지 않고, 아래의 과정들을 통해 자동으로 insert 문을 생성하게 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        member.setId(key.longValue());

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);

        return result.stream().findAny();
        // List -> Optional<> 로 변환해주는 방법 : .findAny()
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));

            return member;
        };
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
        // jdbcTemplate.query()의 return type이 List<>이기 때문에 바로 return (Optional<> 로 감쌀 때에만 .findAny() 이용)
    }
}
