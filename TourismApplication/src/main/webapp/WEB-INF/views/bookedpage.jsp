<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<!-- <style>
body {
	background-image:
		url('https://content3.jdmagicbox.com/comp/hyderabad/k8/040pxx40.xx40.151211132125.q3k8/catalogue/nr-bikes-gachibowli-hyderabad-bike-on-rent-harley-davidson-rfr23.jpg?clr=273f3f');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	background-size: 100% 100%;
}
</style> -->
<style>
.container {
	max-width: 800px;
	padding: 50px 70px;
	background-color: transparent;
	border-radius: 10px;
	opacity: 0.9;
}
</style>

</head>
<body>
	<div class="row">
		<div class="container">
			<table class="table-active" border="2" width="70%" cellpadding="2">
				<tr>
					<th class="table-success">CUSTOMER NAME</th>
					<th class="table-warning">HOTEL NAME</th>
					<th class="table-warning">TOURISM NAME</th>
					<th class="table-danger">BOOK PRICE</th>
					<th class="table-warning">CHECKOUTDATE</th>
					<th class="table-warning">CHECKINDATE</th>
					<th class="table-warning">FEEDBACK</th>
				</tr>
				<c:forEach var="customer" items="${customers}">
					<tr class="info">
						<td>${customer.firstName}</td>
						<td>${customer.hotel.hotelName}</td>
						<td>${customer.tourism.tourismName}</td>
						<td>${customer.booking.bookingPrice}</td>
						<td>${customer.booking.checkoutDate}</td>
						<td>${customer.booking.checkinDate}</td>
						<td><a href="/comments/${customer.customerId}">Write
								Comments</a>
					</tr>

				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>