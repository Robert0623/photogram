// (1) 회원정보 수정
function update(userId) {
    const data = $('#profileUpdate').serialize();
    console.log(data);
    const options = {
        type: 'patch',
        url: `/api/user/${userId}`,
        data: data,
        contentType: 'application/x-www-form-urlencoded;charset=utf-8;',
        dataType: 'application/json'
    };

    $.ajax(options)
        .done(res => {
            console.log('update 성공');
        })
        .fail(error => {
            console.log('update 실패');
        })
}