$(document).on('click','#createBook', function() {
   $.ajax({
      type: "POST",
      //url: "http://localhost:8080/books/newBook",
      url: '/books/newBook',
      data: {
      },
    }).then(function(data) {
        $('.book-id').append(data.id);
        //$('#formAddBuddy').attr("action", "http://localhost:8080/books/addBuddy/" + data.id);
        $('#formAddBuddy').attr("action", "/books/addBuddy/" + data.id);
    });
});

$(document).on('submit','#formAddBuddy', function(e) {
    e.preventDefault(); // avoid to execute the actual submit of the form.

    var form = $(this);
    var data = {};
    data['name'] = $('[name="name"]').val();
    data['number'] = $('[name="number"]').val();
    data['address'] = $('[name="address"]').val();

    var url = form.attr('action');

   $.ajax({
      headers: {
        Accept: "application/json; charset=utf-8",
        "Content-Type": "application/json; charset=utf-8"
      },
      type: "POST",
      url: url,
      data: JSON.stringify(data),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function(response) {
          console.log(response);
        //$('.buddy-name').append(response.name);
          $('#bidders').append('<tr><td>' + response.name + '</td><td>' + response.number + '</td><td>' + response.address +'</td></tr>');
      },
      error: function(xhr, status, error) {
        alert(xhr.responseText);
      }

    });
});