<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Account</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
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
                    <li class="nav-item active">
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
        <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#accountForm">Добавить счет</button>
        <br>
        <p>Список счетов:</p>
        <table class="table table-dark">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Имя</th>
                <th scope="col">Остаток</th>
                <th scope="col">Тип</th>
                <th scope="col">Валюта</th>
                <th scope="col">Действия</th>
            </tr>
            </thead>
            <c:forEach var="account" items="${accounts}">
                <tbody>
                <tr>
                    <th scope="row">${account.id}</th>
                    <td>${account.name}</td>
                    <td>${account.amount}</td>
                    <td>${account.accountType}</td>
                    <td>${account.currency.name}</td>
                    <td>
                        <a data-toggle="modal" data-target="#myModal_${account.id}" href="#myModal_${account.id}">Редактировать</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a data-toggle="modal" data-target="#deleteModal_${account.id}" href="#deleteModal_${account.id}">Удалить</a>
                    </td>
                </tr>
                </tbody>

                <!-- modal form update -->
                <div class="modal fade" id="myModal_${account.id}" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title" id="myModalLabel">Редактирование счета</h3>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form method="post" id="account_${account.id}">
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="accountId" class="col-form-label">ID счета:</label>
                                        <input type="text" name="id" class="form-control" readonly value="${account.id}" id="accountId">
                                        <label for="accountName" class="col-form-label">Название счета:</label>
                                        <input type="text" name="name" class="form-control" contenteditable="true" value="${account.name}" id="accountName">
                                        <label for="amountSave" class="col-form-label">Сумма на счете:</label>
                                        <input type="number" name="amount" class="form-control" id="amountSave" value="${account.amount}" min="0.00" max="1000000.00" step="0.01" />
                                        <label for="amount" class="col-form-label">Тип счета:</label>
                                        <select name="accountType" form="account_${account.id}">
                                            <c:forEach var="type" items="${accountTypes}">
                                                <c:choose>
                                                    <c:when test="${account.accountType eq type}">
                                                        <option selected value="${type}">${type}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${type}">${type}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <label for="recipient-name" class="col-form-label">Укажите тип валюты счета:</label>"
                                        <select name="currency" form="account_${account.id}">
                                            <c:forEach var="currency" items="${currencies}">
                                                <c:choose>
                                                    <c:when test="${account.currency.id eq currency.id}">
                                                        <option selected value="${currency.id}">${currency.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${currency.id}">${currency.name}</option>
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
                <div class="modal fade" id="deleteModal_${account.id}" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title" id="deleteModalLabel">Удаление счета</h3>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form method="post">
                                <div class="modal-body">
                                    <div class="form-group">
                                        <p>Вы действительно хотите удалить счет "${account.name}"? </p>
                                        <input type="hidden" name="id" class="form-control" readonly value="${account.id}" id="${account.id}">
                                        <input type="hidden" name="name" class="form-control" contenteditable="true" value="${account.name}" id="${account.id}">
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
    <div class="modal fade" id="accountForm" tabindex="-1" role="dialog" aria-labelledby="requestFormLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title" id="exampleModalLabel">Добавить счет</h3>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form method="post" id="accountSave">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="recipient-name" class="col-form-label">Имя счета:</label>
                            <input type="text" name="name" class="form-control" id="recipient-name">
                            <label for="amount" class="col-form-label">Сумма на счете:</label>
                            <input type="number" name="amount" class="form-control" id="amount" value="0" min="0.00" max="1000000.00" step="0.01" />
                            <label for="amount" class="col-form-label">Выберите тип счета:</label>
                            <select name="accountType" form="accountSave">
                                <option value="CASH">CASH</option>
                                <option value="CREDIT_CARD">CREDIT_CARD</option>
                                <option value="DEBIT_CARD">DEBIT_CARD</option>
                            </select>
                            <label for="recipient-name" class="col-form-label">Укажите тип валюты счета:</label>"
                            <select name="currency" form="accountSave">
                                <c:forEach var="currency" items="${currencies}">
                                    <option value="${currency.id}">${currency.name}</option>
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
