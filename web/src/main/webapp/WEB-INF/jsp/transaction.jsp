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
            width: 60%;
            padding: 10px;
            background: none;
            margin-left: auto;
            margin-right: auto;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script>
        $('.add').on('click', add);
        $('.remove').on('click', remove);
        function add(){
            var new_chq_no = parseInt($('#total_chq').val())+1;
            var new_input="<select name='category_"+new_chq_no+"' form='transactionSave'>\n"+
                "<c:forEach var='category' items='${categories}'>\n" +
                    "<option value='${category.id}'>${category.name}</option>\n" +
                "</c:forEach>\n" +
                "</select>";
            $('#new_chq').append(new_input);
            $('#total_chq').val(new_chq_no)
        }
        function remove(){
            var last_chq_no = $('#total_chq').val();
            if(last_chq_no>1){
                $('#category_'+last_chq_no).remove();
                $('#total_chq').val(last_chq_no-1);
            }
        }
    </script>
</head>
<body class="bg-light">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/">GeekFactory - Home Finance</a>
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
        <form>
            <input type="search" name="search" placeholder="Поиск транзакции по ID">
            <input type="submit" class="btn btn-secondary" value="Найти"></p>
        </form>
        <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#transactionForm">Добавить транзакцию</button>
        <br>
        <p>Списко транзакций:</p>
        <table class="table table-dark">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Имя</th>
                    <th scope="col">Дата создания</th>
                    <th scope="col">Счёт</th>
                    <th scope="col">Категории</th>
                    <th scope="col">Действия</th>
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
                        <td>
                            <a data-toggle="modal" data-target="#myModal_${transaction.id}" href="#myModal_${transaction.id}">Редактировать</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <a data-toggle="modal" data-target="#deleteModal_${transaction.id}" href="#deleteModal_${transaction.id}">Удалить</a>
                        </td>
                    </tr>
                </tbody>

                <!-- modal form update -->
                <div class="modal fade" id="myModal_${transaction.id}" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title" id="myModalLabel">Редактирование счета</h3>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form method="post" id="transaction_${transaction.id}">
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="transactionId" class="col-form-label">ID транзакции:</label>
                                        <input type="text" name="id" class="form-control" readonly value="${transaction.id}" id="transactionId">
                                        <label for="transactionName" class="col-form-label">Имя транзакции:</label>
                                        <input type="text" name="name" class="form-control" value="${transaction.name}" id="transactionName">
                                        <label for="transactionDateTime" class="col-form-label">Дата транзакции:</label>
                                        <input type="datetime-local" name="dateTime" class="form-control" value="${transaction.dateTime}" id="transactionDateTime">
                                        <label for="dateTime" class="col-form-label">Редактирование категорий транзакции:</label>
                                        <select multiple name="categories" form="transaction_${transaction.id}">
                                            <option value="">-</option>
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category.id}">${category.name}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="recipient-name" class="col-form-label">Укажите тип счета:</label>
                                        <select name="account" form="transaction_${transaction.id}">
                                            <c:forEach var="account" items="${accounts}">
                                                <c:choose>
                                                    <c:when test="${account.name eq transaction.account.name}">
                                                        <option selected value="${account.id}">${account.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${account.id}">${account.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Отменить</button>
                                    <input type="hidden" name="_method" value="put">
                                    <input type="submit" class="btn btn-primary" value="Сохранить">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- modal form delete -->
                <div class="modal fade" id="deleteModal_${transaction.id}" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title" id="deleteModalLabel">Удаление транзакции</h3>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form method="post">
                                <div class="modal-body">
                                    <div class="form-group">
                                        <p>Вы действительно хотите удалить транзакцию "${transaction.name}"? </p>
                                        <input type="hidden" name="id" class="form-control" readonly value="${transaction.id}" id="${transaction.id}">
                                        <input type="hidden" name="name" class="form-control" contenteditable="true" value="${transaction.name}" id="${transaction.id}">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Нет</button>
                                    <input type="hidden" name="_method" value="delete">
                                    <input type="submit" class="btn btn-primary" value="Удалить">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </table>
    </div>

    <!-- modal form save -->
    <div class="modal fade" id="transactionForm" tabindex="-1" role="dialog" aria-labelledby="requestFormLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title" id="exampleModalLabel">Добавить транзакцию</h3>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form method="post" id="transactionSave">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="recipient-name" class="col-form-label">Имя транзакции:</label>
                            <input type="text" name="name" class="form-control" id="recipient-name">
                            <label for="dateTime" class="col-form-label">Дата транзакции:</label>
                            <input type="datetime-local" name="dateTime" class="form-control" id="dateTime">
                            <label for="dateTime" class="col-form-label">Добавление категорий транзакции:</label>
                            <button type="button" class="btn btn-secondary" onclick="add()">Add</button>
                            <button type="button" class="btn btn-secondary" onclick="remove()">Remove</button>
                            <div class="form-group" id="new_chq"></div>
                            <input name="category_counter" type="hidden" value="1" id="total_chq">
                            <label for="recipient-name" class="col-form-label">Укажите тип счета:</label>
                            <select name="account" form="transactionSave">
                                <c:forEach var="account" items="${accounts}">
                                    <option value="${account.id}">${account.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Отменить</button>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
