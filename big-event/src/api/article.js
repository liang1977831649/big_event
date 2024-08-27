//导入axios对象
//文章分类相关的函数
import request from "@/utils/request.js";
export const  categoryGetAll  = async () => {
  // var token= useTokenStore().token;
  // var category= request.get("/category",{headers:{"Authorization":token}});
  var category = await request.get("/category");
  return category;
};
export const addCategory = async (categoryData) => {
  await request.post("/category", categoryData);
};

export const updateCategory =async (categoryData) => {
  await request.put("/category", categoryData);
};

export const deleteCategory =async (id) => {
  await request.delete("/category?id=" + id);
};


// 文章列表相关的函数
export const getArticleList=async (pageNum,pageSize,state,categoryId)=>{
  const articleList=await request.get('/article?pageNum='+pageNum+"&pageSize="+pageSize+"&state="+state+"&categoryId="+categoryId);
  return articleList;
}

//新增文章函数
export const addArticle=async (articleModel)=>{
  // console.log(articleModel);
  await request.post('/article',articleModel);
}

//编辑文章函数
export const editArticle =async (article)=>{
  await request.put('/article',article);
}
//删除文章
export const deleteArticle= async (id)=>{
 await request.delete('/article?id='+id);
}