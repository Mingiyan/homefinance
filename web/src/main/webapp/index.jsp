<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Geekfactory</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        .center {
            margin-top: 100px;
            width: 80%;
            padding: 10px;
            background: none;
            margin-left: auto;
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
                <li class="nav-item active">
                    <a class="nav-link" href="/">Главная</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/account">Счета</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/currency">Валюты</a>
                </li>
                <li class="nav-item">
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
    <a class="btn btn-secondary" href="/account" role="button">Счета</a>
    <a class="btn btn-secondary" href="/currency" role="button">Валюты</a>
    <a class="btn btn-secondary" href="/transaction" role="button">Транзакции</a>
    <a class="btn btn-secondary" href="/category" role="button">Категории</a>
</div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
