/**
 * @see https://umijs.org/docs/max/access#access
 * */
export default function access(initialState: { loginUser?: API.UserVO; } | undefined) {
// ??是空值合并运算符，它只在左侧的操作数为null或undefined时，才返回右侧的操作数。如果左侧的操作数不是null或undefined，则返回左侧的操作数的值。
  const { loginUser } = initialState ?? {};
  return {
    canAdmin: loginUser && loginUser.userRole === 'admin',
  };
}
