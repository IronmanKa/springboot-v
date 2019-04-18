/**
 * Created by Administrator on 2017/6/15 0015.
 */
(function (Vue) {
    // 页面发送请求
    Vue.prototype.http = function (url, data, success, error) {
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: success,
            error: error
        })
    };
    Vue.prototype.get = function (url, params, success, error) {
        $.ajax({
            url: url,
            type: 'get',
            dataType: 'json',
            contentType: 'application/json',
            data: params,
            success: success,
            error: error
        })
    };
    <!-- value 格式为13位unix时间戳 -->
    <!-- 10位unix时间戳可通过value*1000转换为13位格式 -->
    Vue.filter('datetime', function (value) {
        var date = new Date(value);
        Y = date.getFullYear(),
            m = date.getMonth() + 1,
            d = date.getDate(),
            H = date.getHours(),
            i = date.getMinutes(),
            s = date.getSeconds();
        if (m < 10) {
            m = '0' + m;
        }
        if (d < 10) {
            d = '0' + d;
        }
        if (H < 10) {
            H = '0' + H;
        }
        if (i < 10) {
            i = '0' + i;
        }
        if (s < 10) {
            s = '0' + s;
        }
        <!-- 获取时间格式 2017-01-03 10:13:48 -->
        // var t = Y+'-'+m+'-'+d+' '+H+':'+i+':'+s;
        <!-- 获取时间格式 2017-01-03 -->
        var t = Y + '-' + m + '-' + d + ' ' + H + ':' + i;
        return t;
    });

    Vue.filter('date', function (value) {
        var date = new Date(value);
        Y = date.getFullYear(),
            m = date.getMonth() + 1,
            d = date.getDate();
        if (m < 10) {
            m = '0' + m;
        }
        if (d < 10) {
            d = '0' + d;
        }
        <!-- 获取时间格式 2017-01-03 10:13:48 -->
        // var t = Y+'-'+m+'-'+d+' '+H+':'+i+':'+s;
        <!-- 获取时间格式 2017-01-03 -->
        var t = Y + '-' + m + '-' + d;
        return t;
    });

    Vue.filter('money', function (value) {
        if (value === '') {
            return 0.00
        }
        if (isNaN(value)) {
            return 'NaN'
        }
        return parseFloat(value).toFixed(2)
    })

    Vue.filter('wxBtnAction', function (type) {
        if (type === 'click') {
            return 'Event Key'
        } else if (type === 'view') {
            return 'URL'
        } else if (type === 'media_id' || type === 'view_limited') {
            return '素材'
        } else {
            return 'Action'
        }
    })
})(Vue)