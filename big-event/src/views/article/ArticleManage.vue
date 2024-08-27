<script setup>

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

import { Plus } from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const articleModel = ref({
    title: '',
    categoryId: '',
    coverImg: '',
    content: '',
    state: ''
})

import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'

//文章分类数据模型
const categorys = ref([
    {
        "id": 3,
        "categoryName": "美食",
        "categoryAlias": "my",
        "createTime": "2023-09-02 12:06:59",
        "updateTime": "2023-09-02 12:06:59"
    },
    {
        "id": 4,
        "categoryName": "娱乐",
        "categoryAlias": "yl",
        "createTime": "2023-09-02 12:08:16",
        "updateTime": "2023-09-02 12:08:16"
    },
    {
        "id": 5,
        "categoryName": "军事",
        "categoryAlias": "js",
        "createTime": "2023-09-02 12:08:33",
        "updateTime": "2023-09-02 12:08:33"
    }
])
//定义文章的修改或添加
const title = ref('添加文章')

//用户搜索时选中的分类id
const categoryId = ref('')

//用户搜索时选中的发布状态
const state = ref('')

//文章列表数据模型
const articles = ref([
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "categoryName": "旅游",
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "categoryName": "旅游",
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "categoryName": "旅游",
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
])

import { categoryGetAll, getArticleList, addArticle, editArticle, deleteArticle } from '@/api/article.js'

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = async (size) => {
    pageSize.value = size
    //触发列表查询函数
    const articleListData = await getArticleList(pageNum.value, pageSize.value, state.value, categoryId.value);
    articles.value = articleListData.data.items;
}
//当前页码发生变化，调用此函数
const onCurrentChange = async (num) => {
    pageNum.value = num
    //触发列表查询函数
    const articleListData = await getArticleList(pageNum.value, pageSize.value, state.value, categoryId.value);
    articles.value = articleListData.data.items;
}

//获取文章分类列表的函数
const categoryGetAlls = async () => {
    const categoryData = await categoryGetAll();
    categorys.value = categoryData.data;
}
categoryGetAlls();

//获取文章列表
const getAticles = async () => {
    const articleListData = await getArticleList(pageNum.value, pageSize.value, state.value, categoryId.value);
    articles.value = articleListData.data.items;//文章
    total.value = articleListData.data.pageNum;//文章总条数
}
getAticles();

//搜索功能
const searchArticles = async () => {
    const articleListData = await getArticleList(pageNum.value, pageSize.value, state.value, categoryId.value);
    articles.value = articleListData.data.items;//文章
    total.value = articleListData.data.pageNum;//文章总条数
}
//重置功能
const reset = () => {
    state.value = '';
    categoryId.value = ''
}

import { useTokenStore } from "@/store/tokenStore.js"
import { ElMessage,ElMessageBox } from 'element-plus'
const tokenStore = useTokenStore();

//设置图上上传后的回调函数
const uploadSuccess = (result) => {
    //将得到的图片路径给articleModel对象赋值
    articleModel.value.coverImg = result.data;
    console.log("result.data=" + result.data);
}

//新增文章函数
const addArticles = async () => {
    await addArticle(articleModel.value);
    ElMessage.success("文章添加成功")
}

//编辑文章
//这个函数的作用：1.传入row，并给articleModel赋值
//2.修改弹窗的标题
const popEditWindows = (row) => {
    title.value = '编辑文章'
    articleModel.value = row;
    //打开编辑窗口
    visibleDrawer.value = true

}

//真正的编辑函数了
const editArticles = async () => {
    await editArticle(articleModel.value);
    ElMessage.success('文章编辑成功')
}

//编辑文章和新增文章的函数，都是同一个按钮
const ifEditAndAddArticle = (type) => {
    articleModel.value.state = type;
    console.log("articleModel.value=", articleModel.value);


    //不管你是发布还是编辑文章，都会得到被赋值的articleModel对象，并将该对象作为参数给函数处理
    if (title.value === '编辑文章') {
        //调用编辑函数
        editArticles();
    } else {
        //调用当前的函数
        addArticles();
    }
    //关闭弹窗
    visibleDrawer.value = false
    //将ArticleModel数据清楚
    articleModel.value.title = '';
    articleModel.value.categoryId = '';
    articleModel.value.coverImg = '';
    articleModel.value.content = '';
    articleModel.value.state = '';
    //刷新
    getAticles();
}

//删除文章函数
const deleteArticles = async (row) => {
    ElMessageBox.confirm(
        '你确认要删除该文章信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: "warning",
        })
        .then(async () => {
            //调用接口
             await deleteArticle(row.id);
            ElMessage.success('文章删除成功')
            //刷新列表
            getAticles();
        })
        .catch(() => {
            ElMessage.info('用户取消了删除')
        })
}

//添加校验规则
const rules = {
    title: [
        { required: true, message: '请输入标题', trigger: 'blur' },
        { min: 3, max: 10, message: '标题长度为3-10的非空字符串', trigger: 'blur' },
    ],
    category: [
        { required: true, message: "请选择文章所属分类", trigger: 'blur' }
    ],
    img: [
        { required: true, message: "请上传文章封面", trigger: 'blur' }
    ],
    content: [
        { required: true, message: "请输入文章内容", trigger: 'blur' }
    ]
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true; title = '添加文章'">添加文章</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="文章分类：">
                <el-select placeholder="请选择" v-model="categoryId">
                    <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="发布状态：">
                <el-select placeholder="请选择" v-model="state">
                    <el-option label="已发布" value="已发布"></el-option>
                    <el-option label="草稿" value="草稿"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="searchArticles()">搜索</el-button>
                <el-button @click="reset()">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 文章列表 -->
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="文章标题" width="400" prop="title"></el-table-column>
            <el-table-column label="分类" prop="categoryName"></el-table-column>
            <el-table-column label="发表时间" prop="createTime"> </el-table-column>
            <el-table-column label="状态" prop="state"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="popEditWindows(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteArticles(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; 
        justify-content: flex-end" />
        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
            <!-- 添加文章表单、修改文章表单 -->
            <el-form :model="articleModel" label-width="100px" :rules="rules">
                <el-form-item label="文章标题" prop="title">
                    <el-input v-model="articleModel.title" placeholder="请输入标题"></el-input>
                </el-form-item>
                <el-form-item label="文章分类" prop="category">
                    <!-- 这里只是改变了categoryId，没有改变到categoryName，不过不影响，因为数据库不存储categoryName -->
                    <el-select placeholder="请选择" v-model="articleModel.categoryId">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文章封面" prop="img">

                    <!-- 
                    1.action-upload:设置是否自动上传
                    2.action:设置后端服务器的访问路径
                    3.name:设置上传的字段名，也就是请求方法的参数名
                    4.headers：设置上传的请求头，因为会存在跨域问题
                    5.on-success:设置上传成功的回调函数
                     -->
                    <el-upload 
                    class="avatar-uploader" 
                    :auto-upload="true" 
                    :show-file-list="false" 
                    action="http://127.0.0.1:8080/upload"
                    name="multipartFile" 
                    :headers="{ 'Authorization': tokenStore.token }" 
                    :on-success="uploadSuccess">
                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="文章内容" prop="content">
                    <div class="editor">
                        <quill-editor theme="snow" v-model:content="articleModel.content" contentType="html">
                        </quill-editor>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="ifEditAndAddArticle('已发布')">发布</el-button>
                    <el-button type="info" @click="ifEditAndAddArticle('草稿')">草稿</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

/* 抽屉样式 */
.avatar-uploader {
    :deep {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>