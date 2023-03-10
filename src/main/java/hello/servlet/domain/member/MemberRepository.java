package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 동시성 문제가 고려되어 있지 안아서
// 실무에서는 ConcurrentHashMap, AtomicLong을 사용하는 걸 고려해야 함
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() { return instance; }

    private MemberRepository() {}

    public Member save(Member member) {
        member.setId(++sequence); // id값을 별도로 설정
        store.put(member.getId(), member); // map에 저장
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
