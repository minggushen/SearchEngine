<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/baidu.ico"/>
    <link rel="bookmark" href="${pageContext.request.contextPath}/img/baidu.ico"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
    <title>${requestScope.keywords }&nbsp;-&nbsp;摆渡搜索</title>
    <style>
        div.header {
            padding-top: 1%;
            /* position:fixed;
            top:0;
            background: #fff;
            z-index: 1000;  */
        }

        a.title {
            font-size: 17px;
            color: #0000ff;
        }

        a.link {
            color: #3baf3b;
        }

        div.content {
            font-size: 15px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
        }

        span.time {
            color: #777777;
        }
    </style>
</head>
<body>
<div class="header">
    <div class="row">
        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
            <img class="img-responsive" src="${pageContext.request.contextPath}/img/logo1.png">
        </div>
        <div class="col-lg-4">
            <form role="form" action="query">
                <div class="input-group">
                    <input type="text" class="form-control" id="keywords" name="keywords"
                           value="${requestScope.keywords }">
                    <span class="input-group-btn">
                        	<input class="btn btn-primary" type="submit" id="submit" value="摆渡一下">
                    	</span>
                </div>
            </form>
        </div>
    </div>
    <hr style="height:2px;border:none;border-top:2px groove skyblue;"/>
</div>

<table class="table">
    <tr>
        <td>分数</td>
        <td>文件地址</td>
        <td>文件内容</td>
    </tr>
    <c:forEach var="page" items="${requestScope.docList }">
        <tr>
            <td border=“1px”>${page.docIndexScore }</td>
            <td border=“1px”>${page.path }</td>
            <td border=“1px”>
                <c:forEach var="content" items="${page.searchHighlightTextResult}">
                    <div border=“1px”>${content}</div><div></div>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="row">
    <div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 col-lg-offset-1 col-md-offset-1 col-sm-offset-1 col-xs-offset-1">
        <ul class="pagination pagination-lg">
            <c:if test="${requestScope.pageInfo.currentPage > 1}">
                <li>
                    <a href="${pageContext.request.contextPath}/query?keywords=${requestScope.keywords}&pageId=${requestScope.pageInfo.currentPage-1}">上一页</a>
                </li>
            </c:if>
            <c:forEach var="i" begin="0" end="9">
                <c:if test="${(requestScope.pageInfo.startPage + i) <= requestScope.pageInfo.totalPage}">
                    <c:choose>
                        <c:when test="${requestScope.pageInfo.currentPage == (requestScope.pageInfo.startPage + i) }">
                            <li class="active"><a
                                    href="${pageContext.request.contextPath}/query?keywords=${requestScope.keywords}&pageId=${requestScope.pageInfo.startPage + i}">${requestScope.pageInfo.startPage + i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="${pageContext.request.contextPath}/query?keywords=${requestScope.keywords}&pageId=${requestScope.pageInfo.startPage + i}">${requestScope.pageInfo.startPage + i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>
            <c:if test="${requestScope.pageInfo.currentPage < requestScope.pageInfo.totalPage }">
                <li>
                    <a href="${pageContext.request.contextPath}/query?keywords=${requestScope.keywords}&pageId=${requestScope.pageInfo.currentPage+1}">下一页</a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
</body>
</html>