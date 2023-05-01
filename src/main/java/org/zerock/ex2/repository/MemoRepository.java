package org.zerock.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ex2.entity.Memo;

import javax.transaction.Transactional;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

//    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    Page<Memo> findByMnoBetween(Long from, long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);

    // @Query 어노테이션
    @Query("SELECT m FROM Memo m ORDER BY m.mno DESC")
    List<Memo> getListDesc();

    // @Query 어노테이션 + 파라미터 바인딩
    @Transactional
    @Modifying
    @Query("UPDATE Memo m SET m.memoText = :memoText WHERE m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);
}
