package org.zerock.ex2.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Commit;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

//  INSERT
    @Test
    public void testInsertDummies() {

        IntStream.rangeClosed(1, 100).forEach(i ->  {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

//    SELECT
    @Test
    public void testSelect() {
        Long mno = 100L;
        // null이어도 오류 발생시키지 않음
        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("================================");
        // result에 값이 담겼으면
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

//    SELECT
    @Test
    @Transactional
    public void testSelect2() {
        Long mno = 100L;
        Memo memo = memoRepository.getById(mno);
        System.out.println("===================");
        System.out.println(memo);
    }

//    UPDATE
    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
    }

//    DELETE
    @Test
    public void testDelete() {
        Long mno = 12L;
        memoRepository.deleteById(mno);
    }

    // 페이징 처리
    @Test
    public void testPageDefault() {

        // 1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("===============================");
        System.out.println("Total Pages : " + result.getTotalPages());
        System.out.println("Total Count : " + result.getTotalElements());
        System.out.println("Page Number : " + result.getNumber());
        System.out.println("Page Size : " + result.getSize());
        System.out.println("has next page? : " + result.hasNext());
        System.out.println("first page? : " + result.isFirst());
    }

    // 정렬 조건
    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    // 쿼리 메소드
    @Test
    public void testQueryMethods() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    // 쿼리 메소드와 Pageable 결합
    @Test
    public void testQueryMethodsWithPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(memo -> System.out.println(memo));
    }

    // deleteBy로 시작하는 삭제 처리
    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

    // @Query 어노테이션
    @Test
    public void testQuery2() {
        List<Memo> list = memoRepository.getListDesc();
        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    // @Query 어노테이션 + 파라미터 바인딩
    @Test
    public void testUpdate2() {
        int result = memoRepository.updateMemoText(11L, "today");
        Optional<Memo> result1 = memoRepository.findById(11L);
        if (result1.isPresent()) {
            Memo memo = result1.get();
            System.out.println(memo);
        }
    }
}
