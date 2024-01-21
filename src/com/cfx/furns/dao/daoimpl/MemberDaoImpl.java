package com.cfx.furns.dao.daoimpl;

import com.cfx.furns.dao.BasicDao;
import com.cfx.furns.dao.IMemberDao;
import com.cfx.furns.entity.Member;
import com.cfx.furns.utils.MD5Utils;

import java.util.List;

import static com.cfx.furns.utils.Constants.TABLE_MEMBER;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:17
 * <p>
 * 会员 MemberDao 接口实现类
 **/
public class MemberDaoImpl extends BasicDao<Member> implements IMemberDao {
    private static final String TAG = "MemberDaoImpl";

    @Override
    public Member queryMemberByName(String userName) {
        if (userName == null || "".equals(userName)) {
            return null;
        }
        String sql = "select * from " + TABLE_MEMBER + " where username = ?";
        return querySingle(sql, Member.class, userName);
    }

    @Override
    public int saveMember(Member member) {
        if (member == null) {
            return 0;
        }
        String sql = "insert into member values(null, ?, ?, ?)";
        return update(sql, member.getUserName(), MD5Utils.stringToMD5(member.getPassword()), member.getEmail());
    }

    @Override
    public List<Member> getAllMembers() {
        String sql = "select * from " + TABLE_MEMBER;
        return queryMulti(sql, Member.class, null);
    }
}
