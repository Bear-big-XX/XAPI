export default [
  {name:'登录', path: '/user', layout: false, routes: [{ path: '/user/login', component: './User/Login' }] },
  { name:'欢迎',path: '/welcome', icon: 'smile', component: './Welcome' },
  {path: '/interface_info/:id',name:'查看接口',icon:'smile', component: './InterfaceInfo',hideInMenu:true},
  {
    name:'管理员界面',
    path: '/admin',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      { path: '/admin', redirect: '/admin/sub-page' },
      { path: '/admin/sub-page', component: './Admin' },
    ],
  },
  { name:'表格页',icon: 'table', path: '/list', component: './TableList' },
  { path: '/', redirect: '/welcome' },
  { path: '*', layout: false, component: './404' },
];
