/**
 * Created by 当乐 on 2017/6/19.
 */
(function (Vue) {

    var menuItem = Vue.extend({
        name: 'menu-item',
        template: '' +
        '<el-submenu :data-id="menu.id" v-if="menu.children" :index="pid + \'>\' + menu.name">' +
        '<template slot="title"><i class="el-icon-message"></i>{{menu.name}}</template>' +
        '<template v-for="item in menu.children">' +
        '<menu-item :menu="item" :pid="pid + \'>\' + menu.name"></menu-item>' +
        '</template>' +
        '</el-submenu>' +
        '<el-menu-item :data-id="menu.id" :data-menu-path="pid + \'>\' + menu.name" :data-href="menu.url" :data-title="menu.name" v-else :index="pid + \'>\' + menu.name"><i class="el-icon-menu"></i>{{menu.name}}</el-menu-item>',
        props: {
            menu: {
                type: Object,
                default: function () {
                    return {}
                }
            },
            pid: String
        },
        methods: {
        }
    })
    Vue.component('menu-item', menuItem)

    var leftMenu = Vue.extend({
        name: 'left-item',
        template: '' +
        '<div>' +
        '<el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>' +
        '<el-menu :style="dlstyle" :default-openeds="defaultopeneds" :unique-opened="true">' +
        '<template v-for="item in menus">' +
        '<menu-item :menu="item" :pid="pid"></menu-item>' +
        '</template>' +
        '</el-menu>' +
        '</div>',
        props: {
            pid: String,
            list: {
                type: Array,
                default: function () {
                    return []
                }
            },
            defaultopeneds:{
                type: Array,
                default: function () {
                    return []
                }
            },
            dlstyle: String
        },
        data: function () {
            return {
                filterText: '',
                menus: []
            }
        },
        beforeMount: function () {
            this.menus = this.list
        },
        methods: {
            filterMenuByName: function (list, keyword) {
                var arr = []
                var that = this
                list.forEach(function (obj) {
                    if (obj.children) {
                        var res = that.filterMenuByName(obj.children, keyword)
                        if (res.length != 0) {
                            arr = arr.concat(res)
                        }
                    } else {
                        if (obj.name.indexOf(keyword) != -1) {
                            arr.push(obj)
                            return
                        }
                    }
                })
                return arr
            }
        },
        watch: {
            list: function (val) {
                this.menus = val
                this.filterText = ''
            },
            filterText: function (val) {
                if (val == '') {
                    this.menus = this.list
                    return
                }
                var arr = this.filterMenuByName(this.list, val)
                this.menus = arr
            }
        }
    })
    Vue.component('left-menu', leftMenu)

    // 选择游戏下拉项组件
    Vue.component('game-select-item', {
        functional: true,
        render: function (h, ctx) {
            var item = ctx.props.item;
            return h('li', ctx.data, [
                h('div', [item.name])
            ]);
        },
        props: {
            item: { type: Object, required: true }
        }
    });

    var yyMenuTreeItemContent = Vue.extend({
        name: 'yy-menu-tree-item-content',
        template: '<div style="display: inline-block; align-items: center; width: 300px">' +
        '<span>{{name}}</span>' +
        '<el-button v-if="!hasChild" @click="onFuncBtnClick($event)" style="float: right" type="text">功能设置</el-button>' +
        '</div>',
        props: {
            hasChild: {
                type: Boolean,
                required: true
            },
            id: {
                type: Number,
                required: true
            },
            name: {
                type: String,
                required: true
            },
            funcClickCb: {
                type: Function,
                required: true
            }
        },
        methods: {
            onFuncBtnClick: function (event) {
                event.cancelBubble = true
                this.funcClickCb(this.id)
            }
        }
    })
    Vue.component('yy-menu-tree-item-content', yyMenuTreeItemContent)


}) (Vue);