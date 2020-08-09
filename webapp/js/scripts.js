// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });

  function onSuccess(json, status){
    insertAnswer();
    increaseCountOfAnswer();

    function insertAnswer() {
      var answer = json.answer;
      var answerTemplate = $("#answerTemplate").html();
      var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
      $(".qna-comment-slipp-articles").prepend(template);
    }

    function increaseCountOfAnswer() {
      var question = json.question;
      $(".qna-comment-count-number").text(question.countOfComment);
    }
  }

  function onError(xhr, status) {
    alert("error");
  }
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".form-answer-delete button[type=submit]").click(deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();

  var deleteBtn = $(this);
  var queryString = deleteBtn.closest('form').serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/deleteAnswer',
    data : queryString,
    dataType : 'json',
    error: () => {alert('error')},
    success : () => {alert('success')},
  });
}