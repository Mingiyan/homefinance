<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Category</title>
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
                    <li class="nav-item">
                        <a class="nav-link" href="/account">Счета</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/currency">Валюты</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/transaction">Транзакции</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/category">Категории</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="center">
        <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#categoryForm">Добавить категорию</button>
        <br>
        <p>Список категорий транзакций:</p>
        <table class="table table-dark">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Имя</th>
                <th scope="col">Родительская категория</th>
                <th scope="col">Действия</th>
            </tr>
            </thead>
            <c:forEach var="category" items="${categories}">
                <tbody>
                <tr>
                    <th scope="row">${category.id}</th>
                    <td>${category.name}</td>
                    <td>${category.parentCategory.name}</td>
                    <td>
                        <a data-toggle="modal" data-target="#myModal_${category.id}" href="#myModal_${category.id}">Редактировать</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a data-toggle="modal" data-target="#deleteModal_${category.id}" href="#deleteModal_${category.id}">Удалить</a>
                    </td>
                </tr>
                </tbody>
                <!-- modal form update -->
                <div class="modal fade" id="myModal_${category.id}" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title" id="myModalLabel">Редактирование валюты</h3>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form method="post" id="parentCategory_${category.id}">
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="categoryId" class="col-form-label">ID категории:</label>
                                        <input type="text" name="id" class="form-control" readonly value="${category.id}" id="categoryId">
                                        <label for="categoryName" class="col-form-label">Имя категории:</label>
                                        <input type="text" name="name" class="form-control" contenteditable="true" value="${category.name}" id="categoryName">
                                        <label for="categoryName" class="col-form-label">Родительская категория:</label>
                                        <select name="parent_${category.id}" form="parentCategory_${category.id}">
                                            <option selected>-</option>
                                            <c:forEach var="parentCategory" items="${categories}">
                                                <c:choose>
                                                    <c:when test="${category.parentCategory eq parentCategory}">
                                                        <option selected value="${parentCategory.id}">${parentCategory.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${parentCategory.id}">${parentCategory.name}</option>
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
                <div class="modal fade" id="deleteModal_${category.id}" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title" id="deleteModalLabel">Редактирование валюты</h3>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form method="post">
                                <div class="modal-body">
                                    <div class="form-group">
                                        <p>Вы действительно хотите удалить категорию "${category.name}"? </p>
                                        <input type="hidden" name="id" class="form-control" readonly value="${category.id}" id="${category.id}">
                                        <input type="hidden" name="name" class="form-control" contenteditable="true" value="${category.name}" id="${category.id}">
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
    <div class="modal fade" id="categoryForm" tabindex="-1" role="dialog" aria-labelledby="requestFormLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title" id="exampleModalLabel">Добавить категорию</h3>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form method="post" id="category">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="recipient-name" class="col-form-label">Имя категории:</label>
                            <input type="text" name="name" class="form-control" id="recipient-name">
                            <label for="recipient-name" class="col-form-label">Родительская категория, если есть:</label>
                            <select name="parentCategory" form="category">
                                <option selected disabled hidden>Выберите родительскую категорию</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}">${category.name}</option>
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
