<script setup>


import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
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

//声明函数
import { categoryGetAll, addCategory, updateCategory, deleteCategory } from "@/api/article.js"
//调用函数
const categoryGetAlls = async () => {
    var result = await categoryGetAll();
    categorys.value = result.data;
}
categoryGetAlls();

//弹窗的标题
const title = ref('添加分类');

//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const categoryModel = ref({
    categoryName: '',
    categoryAlias: ''
})
//添加分类表单校验
const rules = {
    categoryName: [
        { required: true, message: '请输入分类名称', trigger: 'blur' },
    ],
    categoryAlias: [
        { required: true, message: '请输入分类别名', trigger: 'blur' },
    ]
}
//添加分类判断
import { ElMessage,ElMessageBox } from 'element-plus'
//因为添加，修改都是一个表单，所以点击确定的时候需要判断一下
const updateAndAdd = async () => {
    if (title.value === '添加分类') {
        await addCategorys();
    } else if (title.value === '修改分类') {
        await updateCategorys();

    }
    //刷新页面
    categoryGetAlls();
    categoryModel.value.categoryName = '';
    categoryModel.value.categoryAlias = '';
    categoryModel.value.id = '';

}
//添加文章分类
const addCategorys = async () => {
    await addCategory(categoryModel.value);
    ElMessage.success("添加文章分类成功")
    dialogVisible.value = false;
    // //刷新页面
    // categoryGetAlls();
}

//展示文章分类信息
const showCategory = (row) => {
    title.value = '修改分类';
    dialogVisible.value = true;
    //将row（代表的是对象）的数据封装到categoryModel里，以便后续修改
    console.log("row=", row);
    categoryModel.value.categoryName = row.categoryName;
    categoryModel.value.categoryAlias = row.categoryAlias;
    categoryModel.value.id = row.id;
}
//修改文章分类
const updateCategorys = async () => {
    await updateCategory(categoryModel.value);
    ElMessage.success("修改文章分类成功")
    dialogVisible.value = false;
    // categoryGetAlls();
    //清空

}
//删除分类
const deleteCategorys = async (row) => {
    ElMessageBox.confirm(
        '你确认要删除该分类信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: "warning",
        })
        .then(async () => {
            //调用接口
            await deleteCategory(row.id);
            ElMessage.success("删除文章分类成功")
            //刷新列表
            await categoryGetAlls();
        })
        .catch(() => {
            ElMessage.info('用户取消了删除')
        })
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章分类</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible = true; title = '添加分类'">添加分类</el-button>
                </div>
            </div>
        </template>
        <el-table :data="categorys" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="分类名称" prop="categoryName"></el-table-column>
            <el-table-column label="分类别名" prop="categoryAlias"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showCategory(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCategorys(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
    </el-card>

    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
        <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
            <el-form-item label="分类名称" prop="categoryName">
                <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="分类别名" prop="categoryAlias">
                <el-input v-model="categoryModel.categoryAlias" minlength="1" maxlength="15"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="updateAndAdd()"> 确认 </el-button>
            </span>
        </template>
    </el-dialog>
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
}</style>