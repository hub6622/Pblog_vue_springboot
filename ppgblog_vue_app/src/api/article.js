import request from '@/utils/request.js'

export const articleListService = () => {
    return request.get('/article/list')
}
export const articleListByCategoryService = (cate) => {
    return request.get('/article/getArticleByCategory/'+cate)
}
export const articleListByIdService = (id) => {
    return request.get('/article/getArticleById/'+id)
}
export const commentsByArticleIdService = (id) => {
    return request.get('/article/commentsByArticleId/'+id)
}
export const addCommentService = (params) => {
    return request.post('/article/addArticleComment',params)
}
export const addCommentReplyService = (params) => {
    return request.post('/article/addArticleCommentReply',params)
}
export const addArticleService = (params) => {
    return request.post('/article/addArticle',params)
}
export const updateArticleService = (articleId) => {
    return request.post('/article/updateArticleViewCounts/'+articleId)
}
export const getCategoryService = () => {
    return request.get('/article/getCategory')
}
export const hotArticleService = () => {
    return request.get('/article/hotArticle')
}
export const getArticleByUserService = () => {
    return request.get('/article/getArticleByUser')
}
export const deleteArticleService = (articleId) => {
    return request.post('/article/deleteArticle',{articleId:articleId})
}