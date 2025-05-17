// (1) 회원정보 수정
function update(userId, e) {
    e.preventDefault();

    const data = $('#profileUpdate').serialize();
    console.log(data);

    const options = {
        type: 'patch',
        url: `/api/user/${userId}`,
        data: data,
        contentType: 'application/x-www-form-urlencoded;charset=utf-8;',
        // dataType: 'application/json'
    };

    $.ajax(options)
        .done(res => {
            console.log('성공', res);
            location.href = `/user/${userId}`;
        })
        .fail(error => {
            console.log('실패', error.responseJSON);
            if (error.data == null) {
                alert(JSON.stringify(error.responseJSON.message));
            } else {
                alert(JSON.stringify(error.responseJSON.data));
            }
        })
}