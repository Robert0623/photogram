/**
 2. 스토리 페이지
 (1) 스토리 로드하기
 (2) 스토리 스크롤 페이징하기
 (3) 좋아요, 안좋아요
 (4) 댓글쓰기
 (5) 댓글삭제
 */

// (0) 현재 로그인한 사용자 아이디
const principalId = $('#principalId').val();

// (1) 스토리 로드하기
let page = 0;

function storyLoad() {
    const options = {
        type: 'get',
        url: `/api/image?page=${page}`,
        dataType: 'json'
    };

    $.ajax(options)
        .done(res => {
            console.log(res);

            if (res.data.totalPages <= page) {
                console.log('마지막 페이지');
                return;
            }

            let item = ``;
            res.data.content.forEach(image => item += getStoryItem(image));
            $('#storyList').append(item);
        })
        .fail(error => {
            console.log('오류', error);
        });
}

storyLoad();

function getStoryItem(image) {
    let item =
        `<div class="story-list__item">
            <div class="sl__item__header">
                <div>
                    <img class="profile-image" src="/upload/${image.userResponse.profileImageUrl}"
                         onerror="this.src='/images/person.jpeg'" />
                </div>
                <div>${image.userResponse.name}</div>
            </div>

            <div class="sl__item__img">
                <img src="/upload/${image.postImageUrl}" />
            </div>

            <div class="sl__item__contents">
                <div class="sl__item__contents__icon">

                    <button>`;

    if (image.likeState) {
        item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
    } else {
        item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
    }

    item += `
                    </button>
                </div>

                <span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>

                <div class="sl__item__contents__content">
                    <p>${image.caption}</p>
                </div>
                
                <div id="storyCommentList-${image.id}">`;

    image.comments.forEach(comment => {
        item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"">
                    <p>
                        <b>${comment.username} :</b> ${comment.content}
                    </p>`;

        if (principalId == comment.userId) {
            item += `<button onclick="deleteComment(${comment.id})">
                        <i class="fas fa-times"></i>
                    </button>`;
        }

        item +=  `</div>`;
    })



        item +=
            `
            </div>

            <div class="sl__item__input">
                <input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
                <button type="button" onClick="addComment(${image.id})">게시</button>
            </div>

        </div>`;

    return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
    // console.log('윈도우 scrollTop', $(window).scrollTop());
    // console.log('문서의 높이', $(document).height());
    // console.log('윈도우 높이', $(window).height());

    let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());

    if (checkNum < 1 && checkNum > -1) {
        page++;
        storyLoad();
    }
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
    let likeIcon = $(`#storyLikeIcon-${imageId}`);
    if (likeIcon.hasClass("far")) { // 좋아요 하겠다
        const options = {
            type: 'post',
            url: `/api/image/${imageId}/likes`,
            dataType: 'json'
        };

        $.ajax(options)
            .done(res => {
                likeIcon.addClass("fas");
                likeIcon.addClass("active");
                likeIcon.removeClass("far");

                const likeCountStr = $(`#storyLikeCount-${imageId}`).text();
                const likeCount = Number(likeCountStr);
                $(`#storyLikeCount-${imageId}`).text(likeCount + 1);
            })
            .fail(error => {
                console.log('좋아요 하기 실패', error);
            });
    } else { // 좋아요 취소하겠다
        const options = {
            type: 'delete',
            url: `/api/image/${imageId}/likes`,
            dataType: 'json'
        };

        $.ajax(options)
            .done(res => {
                likeIcon.removeClass("fas");
                likeIcon.removeClass("active");
                likeIcon.addClass("far");

                const likeCountStr = $(`#storyLikeCount-${imageId}`).text();
                const likeCount = Number(likeCountStr);
                $(`#storyLikeCount-${imageId}`).text(likeCount - 1);
            })
            .fail(error => {
                console.log('좋아요 취소 실패', error);
            });
    }
}

// (4) 댓글쓰기
function addComment(imageId) {

    let commentInput = $(`#storyCommentInput-${imageId}`);
    let commentList = $(`#storyCommentList-${imageId}`);

    let data = {
        imageId: imageId,
        content: commentInput.val()
    }

    if (data.content === "") {
        alert("댓글을 작성해주세요!");
        return;
    }

    const options = {
        type: 'post',
        url: '/api/comment',
        data: JSON.stringify(data),
        contentType: 'application/json;charset=utf-8',
        dataType: 'json'
    };

    $.ajax(options)
        .done(res => {
            console.log('성공', res);

            const comment = res.data;

            let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			    <p>
			      <b>${comment.username} :</b>
			      ${comment.content}
			    </p>
			    <button onclick="deleteComment(${comment.id})">
                  <i class="fas fa-times"></i>
			    </button>
			  </div>`;
            commentList.prepend(content);
        })
        .fail(error => {
            console.log("댓글 작성 실패", error);
        });

    commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment(commentId) {
    const options = {
        type: 'delete',
        url: `/api/comment/${commentId}`,
        dataType: 'json'
    };

    $.ajax(options)
        .done(res => {
           console.log(res);
           $(`#storyCommentItem-${commentId}`).remove();
        })
        .fail(error => {
            console.log('실패', error);
        });
}