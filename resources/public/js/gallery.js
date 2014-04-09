function deleteimages() {
  var selected = $("input:checked");
  var selectedIds = [];
  selected.each(
    function(){selectedIds.push($(this).attr('id'))});

    if (selectedIds.length < 1) alert ("There is nothing to delete!");
    else
     $.post("/delete",
         { names: selectedIds },
           function(response) {
             var errors = $('<ul>');
             $.each(response,  function(){
             if (this.status == "OK"){
               var element = document.getElementById(this.name);
               $(element).parent().parent().remove();
             }
             else
               errors.append($('<li>',
                            { html: "fail to remove " + this.name + ": " + this.status }));
             });

     if (errors.length > 0)
       $('#error').empty().append(errors)
       }, 'json');


  }
