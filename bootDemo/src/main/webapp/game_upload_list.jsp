<%--
  Created by IntelliJ IDEA.
  User: d.cn
  Date: 2019/1/11
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <head>
        <!-- 引入样式 -->
        <link rel="stylesheet" href="../js/vue/elementui/index.css">
    </head>
    <style>
        a{text-decoration:none}
        .el-header, .el-footer {
            background-color: #B3C0D1;
            color: #333;
            /*text-align: center;*/
            line-height: 60px;
            padding-left: 10px;
        }
        .el-aside {
            background-color: #D3DCE6;
            color: #333;
            text-align: center;
            line-height: 200px;
        }

        .el-main {
            background-color: #E9EEF3;
            color: #333;
            /*text-align: center;*/
            /*line-height: 160px;*/
            padding-bottom: 40px;
            /*padding-left: 5%;*/
        }

        body > .el-container {
            margin-bottom: 40px;
        }
        .el-transfer-panel__list.is-filterable {
            height: 314px !important;
        }
        .el-transfer-panel__body {
            padding-bottom: 36px;
            height: 346px !important;
        }
        .el-select {
            padding-bottom: 10px;
        }
        .el-dialog--small {
            min-height: 400px;
        }
    </style>
    <title>游戏包上传</title>
</head>
<body>
<div class="main el-container" id="main">
    <div class="el-header">
        <el-row>
            <el-col :span="24"><div class="grid-content bg-purple-dark" >
                <a href="javascript:window.history.go(-1);" class="el-icon-arrow-left"></a>
                <span>游戏包上传</span>
                
            </div></el-col>
        </el-row>
    </div>
    <div class="el-main">
        <el-button  size="small" type="primary" style="width: 200px;margin-top: 5px" @click="dialogVisible=true">上传游戏包</el-button>
        <el-select style="float: right;margin-right: 20px;margin-top: 5px" @change="handleChange" v-model="selectApp" filterable placeholder="请选择">
            <el-option
                    v-for="item in options"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
            </el-option>
        </el-select>
        <span style="float: right;margin-top: 10px">游戏筛选:</span>
        <template>
            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column
                        prop="id"
                        label="序号"
                        width="80">
                </el-table-column>
                <el-table-column
                        prop="appId"
                        label="游戏ID"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="游戏名称"
                        width="180"
                        :formatter="appFormat">
                </el-table-column>
                <el-table-column
                        prop="uploadUrl"
                        label="下载地址">
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="上传时间"
                        :formatter="dateFormat">
                </el-table-column>
                <el-table-column
                        prop="creator"
                        label="操作人">
                </el-table-column>
            </el-table>
        </template>
        <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="pageNum"
                :page-size="pageSize"
                layout="total, prev, pager, next"
                :total="totalSize">
        </el-pagination>
        <el-dialog
                title="游戏包上传"
                :visible.sync="dialogVisible"
                width="100%"
                fullscreen="true"
                :before-close="handleClose"
                min-height="400px">
            <template>
                <span>上传游戏:</span>
                <el-select  v-model="uploadGame" filterable placeholder="请选择">
                    <el-option
                            v-for="item in options"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </template>
            <el-upload
                    class="upload-demo"
                    ref="upload"
                    action="/gameUpload"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :file-list="fileList"
                    :on-success="handleSuccess"
                    :before-upload ="beforeUpload"
                    :auto-upload="false"
                    :limit="1"
                    :multiple="false"
                    :on-exceed="handleExceed">
                <el-button slot="trigger" size="medium" type="primary">选取文件</el-button>
                <el-button style="margin-left: 10px;" size="medium" type="success" @click="submitUpload">上传到服务器</el-button>
                <div slot="tip" class="el-upload__tip">悦游游戏包只能上传apk文件</div>
                <span>{{fileUrl}}</span>
            </el-upload>
        </el-dialog>
    </div>
</div>

<!-- 先引入 Vue -->
<script src="../js/vue/vue.js"></script>
<script src="../js/vue/vueComponent.js"></script>
<script src="../js/vue/vue-router.js"></script>
<script src="../js/vue/api.js"></script>
<!-- 引入组件库 -->
<script src="/static/js/vue/elementui/index.js"></script>
<script type="text/javascript">
    var main = new Vue({
        el:'#main',
        data() {
            const generateData2 = _ => {
                const data = new Array();
                let cities = [];
                cities.forEach((e) => {
                    if( e.isShowData==0 ){
                        data.push(e.id);
                    }
                });
                return data;
            };
            return {
                list: [],
                select: [],
                fileList: [],
                options:[],
                fullscreenLoading:false,
                uploadGame:'',
                fileUrl:'',
                dialogVisible:false,
                tableData:[],
                pageNum:1,
                pageSize:20,
                totalSize:0,
                selectApp:''
            };

        },
        methods:{
            //时间格式化
            appFormat:function(row, column) {
                var date = row['appId'];
                if (date == undefined) {
                    return "";
                }
                var aa ='';
                for (var i =0 ;i < this.options.length;i ++){
                    var data = this.options[i];
                    if(data.id == date){
                        aa = data.name;
                        break;
                    }
                }
                return aa;
            },
            dateFormat:function(row, column) {
                var date = row[column.property];
                if (date == undefined) {
                    return "";
                }
                return moment(date).format("YYYY-MM-DD HH:mm:ss");
            },

            handleExceed(files, fileList) {
                this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            },
            handleSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
                this.uploadGame = '';
            },
            beforeUpload(file) {
                console.log(file.type);
                const isJPG = file.type === 'application/vnd.android.package-archive';
                // const isLt2M = file.size / 1024 / 1024 < 2000;

                if (!isJPG) {
                    this.$message.error('上传游戏包只能是 apk 格式!');
                }
                // if (!isLt2M) {
                //     this.$message.error('上传文件大小不能超过 2000MB!');
                // }
                return isJPG;
                // if(this.uploadGame == null || this.uploadGame == ''){
                //     this.$message.error('请先选择上传游戏!');
                //     return false;
                // }
                // return true;
            },
            handleChange(value, direction, movedKeys) {
                console.log(value, direction, movedKeys);
            },
            handleClose(done) {
                // this.$confirm('确认关闭？')
                //     .then(_ => {
                //         done();
                //     })
                //     .catch(_ => {});
                done();
            },
            submitUpload() {
                this.$refs.upload.submit();
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePreview(file) {
                console.log(file);
            },
            beforeUpdate(file){
                console.log("before upload");
            },
            save:function () {
                this.fullscreenLoading = true;
                var show = [];
                for(var i = 0 ; i < this.data2.length; i++){
                    if (this.value2.indexOf(this.data2[i].key) === -1){
                        show.push(this.data2[i].key);
                    }
                }
                $.ajax({
                    url:'${basePath}/channel/data.html?act=savelist',
                    type:'post',
                    dataType:'json',
                    async:true,
                    data: {
                        showlist:JSON.stringify(show), //展示列表
                        blocklist:JSON.stringify(this.value2) //不展示列表
                    },
                    success:function (data) {
                        if (data.statusCode== 200){
                            main.$message({
                                message: data.desc,
                                type: 'success'
                            });
                        }else {
                            main.$message.error(data.message);
                        }
                        main.fullscreenLoading = false;
                    },
                    error:function () {
                        main.fullscreenLoading = false;
                        main.$message.error("出错了....");
                    }
                })

            },
            selectList:function (selectApp) {
                $.ajax({
                    url:'${basePath}/oemsdk/game/gameinfo.html?act=fxAppUploadLog',
                    type:'post',
                    dataType:'json',
                    async:true,
                    data: {
                        appId:this.selectApp, 
                        pageNum:this.pageNum,
                        pageSize:this.pageSize
                    },
                    success:function (data) {
                        this.tableData = data.list;
                        this.totalSize = data.total;
                    }.bind(this),
                    error:function () {
                        main.$message.error("出错了....");
                    }
                })                   
            },
            handleCurrentChange: function (val) {
                this.pageNum = val
                this.selectList()
            },
            handleChange: function () {
                this.pageNum = 1;
                this.selectList()
            },
            
        },
    })
</script>

</body>
</html>
