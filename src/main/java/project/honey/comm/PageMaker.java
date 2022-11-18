package project.honey.comm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;


@Getter
@Setter
public class PageMaker {

    private int startPage;

    private int endPage;

    private boolean prev, next;

    private int realEnd;

    private Long total;

    private Pageable pageable;

    public PageMaker(Pageable pageable, Long total) {

        this.pageable = pageable;

        this.total = total;

        this.endPage = (int) (Math.ceil((pageable.getPageNumber()+1) / 10.0)) * 10; // 페이징의 끝번호

        this.startPage = this.endPage - 9; //시작 페이지

        this.realEnd = (int) (Math.ceil((total * 1.0) / pageable.getPageSize())); // 토탈 페이지의 마지막

        if (realEnd <= this.endPage) {
            this.endPage = realEnd; // 페이징의 끝번호보다 realEnd가 작을 경우 초기화
        }

        this.prev = this.startPage > 1; // 이전 페이지가 있는가

        this.next = this.endPage < realEnd; // 다음 페이지가 있는가
    }
}
