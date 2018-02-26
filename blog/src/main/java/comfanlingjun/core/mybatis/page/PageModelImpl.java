package comfanlingjun.core.mybatis.page;

/**
 * 分页实体实现类
 */
public class PageModelImpl implements PageModelI {

	public static final int DEF_COUNT = 20;
	public int totalCount = 0;
	public int pageSize = 20;
	public int pageNo = 1;

	public PageModelImpl() {
	}

	public PageModelImpl(int pageNo, int pageSize, int totalCount) {
		if (totalCount <= 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
		if (pageSize <= 0) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
		if (pageNo <= 0) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
		if ((this.pageNo - 1) * this.pageSize >= totalCount) {
			this.pageNo = totalCount / pageSize;
			if (this.pageNo == 0) {
				this.pageNo = 1;
			}
		}
	}

	//总记录数
	public int getTotalCount() {
		return totalCount;
	}

	//总页数
	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalCount % pageSize != 0 || totalPage == 0) {
			totalPage++;
		}
		return totalPage;
	}

	//当前页号
	public int getPageNo() {
		return pageNo;
	}

	//每页记录数
	public int getPageSize() {
		return pageSize;
	}

	//是否第一页
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	//是否最后一页
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	//返回下页的页号
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	//返回上页的页号
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}