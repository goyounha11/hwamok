(function ($) {
    $.fn.paging = function (options) {
        let curThis = this;
        let defaults = {
            pageSize: 2, //한 페이지 당 몇개
            currentPage: 1, // 현재 페이지
            pageTotal: 0, // 총 데이터 수
            pageBlock: 10, // 10단위로 끊음
        };

        let subOption = $.extend(true, defaults, options)

        let goPageFnName = null;

        if (subOption.goPageFnName === undefined || subOption.goPageFnName === null || subOption.goPageFnName === '') {
            goPageFnName = "goPage";
        } else {
            goPageFnName = subOption.goPageFnName;
        }

        return this.each(function () {
            let currentPage = subOption.currentPage * 1;
            let pageSize = subOption.pageSize * 1;
            let pageTotal = subOption.pageTotal * 1;
            let pageBlock = subOption.pageBlock * 1;

            let pageTotalCnt = Math.ceil(pageTotal / pageSize) //총 페이지 갯수
            let pageBlockCnt = Math.ceil(currentPage / pageBlock) // 몇개의 블럭이 생기는지 계산
            let startPage, endPage;
            let html = "";

            if (pageBlock > 1) {
                startPage = (pageBlockCnt - 1) * pageBlock + 1;
            }

            if ((pageBlockCnt * pageBlock) >= pageTotalCnt) {
                endPage = pageTotalCnt;
            } else {
                endPage = pageBlockCnt * pageBlock;
            }

            html += `<nav aria-label="Page navigation example">`;
            html += `<ul class="pagination">`;

            if (startPage > 1) {
                html += `<li class="page-item">`;
                html += `<a class="page-link" href="#" onclick="${goPageFnName}(1); return false;">`;
                html += `<span aria-hidden="true">&laquo;</span></a></li>`
            }

            for (let i = startPage; i <= endPage; i++) {
                if (currentPage === i) {
                    html += `<li class="page-item"><a class="page-link">${i}</a></li>`;
                } else {
                    html += `<li class="page-item"><a class="page-link" onclick="${goPageFnName}(${i});">${i}</a></li>`;
                }
            }

            if (endPage < pageTotalCnt) {
                html += `<li class="page-item"><a class="page-link" onclick="${goPageFnName}(${endPage + 1})" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        </a></li>`;
            }

            html += `</ul></nav>`;

            $(curThis).empty().append(html);
        });
    };
})(jQuery);