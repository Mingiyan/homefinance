<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        .center {
            margin-top: 100px;
            width: 80%;
            padding: 10px;
            background: none;
            margin-left: auto;
            margin-right: auto;
        }
    </style>
</head>
<body class="bg-light">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/">GeekFactory</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/">Главная</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/account">Счета</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/currency">Валюты</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/transaction">Транзакции</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/category">Категории</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- Page Content -->
    <div class="center">
        <table class="table table-dark">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Имя</th>
                    <th scope="col">Дата создания</th>
                    <th scope="col">Счёт</th>
                    <th scope="col">Категории</th>
                </tr>
            </thead>
            <c:forEach var="transaction" items="${transactions}">
            <tbody>
                <tr>
                    <th scope="row">${transaction.id}</th>
                    <td>${transaction.name}</td>
                    <td>${transaction.dateTime}</td>
                    <td>${transaction.account.name}</td>
                    <td>
                        <c:forEach var="category" items="${transaction.categoryTransaction}">
                            ${category.name} </br>
                        </c:forEach>
                    </td>
                </tr>
            </tbody>
            </c:forEach>
        </table>
    </div>
</body>
</html>
