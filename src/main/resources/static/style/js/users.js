layui.use(['element', 'table', 'layer', 'jquery'], function () {
    const element = layui.element,
        form = layui.form,
        layer = layui.layer,
        table = layui.table;
    let $ = layui.$;
    let layer_user;
    table.render({
        elem: '#users'
        , id: 'users'
        , height: 488
        , url: '/user/showUsers' //数据接口
        , cellMinWidth: 60
        , page: true //开启分页
        , cols: [[ //表头
            { field: 'id', title: 'ID', sort: true }
            , { field: 'no', title: '学号' }
            , { field: 'name', title: '姓名' }
            , { field: 'lasttime', title: '最后操作时间' }
            , { align: 'center', toolbar: '#operation-bar', fixed: 'right', width: 178 }
        ]]
    });
    $ = layui.$;
    let active = {
        new: function () {
            layer_user = layer.open({
                type: 1,
                title: '添加人员',
                content: $('#add-user').html()
            });
        }
    };
    $('.btn-wrap .layui-btn').on('click', function () {
        const type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    table.on('tool(users)', function(obj){
        const data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('确认删除么', function(index){
                layui.layer.close(index);
                const json = {};
                json.id=data.id;
                return beauty_ajax("/user/deleteUser",json,function () {
                    table.reload('users', {
                        url: "/user/showUsers"
                    });
                });
            });
        } else if(obj.event === 'edit'){
            layer.confirm('确认进行该操作么', function(index){
                layui.layer.close(index);
                layer_user=layer.open({
                    type: 1,
                    title: '修改信息',
                    content: $('#add-user').html()
                });
                $('#user-template form>div').eq(2).addClass('layui-hide');
                $('#user-template button').attr('lay-filter','change-user');
                $('#user-template input').each(function () {
                    const cur_name = this.name;
                    if(data[cur_name] !== undefined){
                        this.value=data[cur_name];
                    }
                })
            });
        }
    });
    form.on('submit(add-user)', function(data){//新建用户
        return beauty_ajax("/user/createUser",data.field,function () {
            table.reload('users', {
                url: "/user/showUsers"
            });
            layer.close(layer_user);
        });
    });
    form.on('submit(change-user)', function(data){//修改用户信息
        return beauty_ajax("/user/updateUser",data.field,function () {
            table.reload('users', {
                url: "/user/showUsers"
            });
            layer.close(layer_user);
        });
    });
})