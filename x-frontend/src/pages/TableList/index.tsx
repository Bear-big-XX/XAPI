import { addRule, removeRule, rule, updateRule } from '@/services/ant-design-pro/api';
import { PlusOutlined } from '@ant-design/icons';
import type { ActionType, ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import {
  FooterToolbar,
  ModalForm,
  PageContainer,
  ProDescriptions,
  ProFormText,
  ProFormTextArea,
  ProTable,
} from '@ant-design/pro-components';
import '@umijs/max';
import { Button, Drawer, Input, message } from 'antd';
import React, { useRef, useState } from 'react';
import UpdateModal, { FormValueType } from './components/UpdateForm';
import UpdateForm from './components/UpdateForm';
import {
  addInterfaceInfoUsingPost,
  deleteInterfaceInfoUsingPost,
  listInterfaceInfoByPageUsingGet,
  offlineInterfaceInfoUsingPost,
  onlineInterfaceInfoUsingPost,
  updateInterfaceInfoUsingPost
} from "@/services/yupi-backend/interfaceInfoController";
import type {SortOrder} from "antd/lib/table/interface";
import {RequestData} from "@ant-design/pro-table/es/typing";
import CreateModal from "@/pages/TableList/components/CreateModal";

/**
 * @en-US Add node
 * @zh-CN 添加节点
 * @param fields
 */
const handleAdd = async (fields: API.RuleListItem) => {
  // 调用 message.loading 方法显示一个加载提示，告诉用户正在处理添加操作。message.loading 方法返回一个函数 hide，这个函数可以用来隐藏加载提示。
  const hide = message.loading('正在添加');
  try {
    await addInterfaceInfoUsingPost({
      ...fields,
    });
    hide();
    message.success('Added successfully');
    return true;
  } catch (error) {
    hide();
    message.error('Adding failed, please try again!');
    return false;
  }
};






const TableList: React.FC = () => {
  /**
   * @en-US Pop-up window of new window
   * @zh-CN 新建窗口的弹窗
   *  */
  const [createModalOpen, handleModalOpen] = useState<boolean>(false);
  /**
   * @en-US The pop-up window of the distribution update window
   * @zh-CN 分布更新窗口的弹窗
   * */
  const [updateModalOpen, handleUpdateModalOpen] = useState<boolean>(false);
  const [showDetail, setShowDetail] = useState<boolean>(false);
  // actionRef：一个引用，用于访问ProTable组件的action属性，以便重新加载表格数据
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.InterfaceInfo>();
  const [selectedRowsState, setSelectedRows] = useState<API.InterfaceInfo[]>([]);

  /**
   * @en-US International configuration
   * @zh-CN 国际化配置
   * */

  /**
   * 发布接口
   * @param selectedRows
   */
  const handleOnline = async (selectedRows: API.IdRequest) => {

    const hide = message.loading('正在发布接口');
    if (!selectedRows) return true;
    try {
      await onlineInterfaceInfoUsingPost({
        id: selectedRows.id,
      });
      hide();
      message.success('接口发布成功，即将刷新！');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('发布接口失败，请重试！');
      return false;
    }
  };

  /**
   *  Delete node
   * @zh-CN 删除节点
   *
   * @param selectedRows
   */
  const handleRemove = async (selectedRows: API.InterfaceInfo) => {
    const hide = message.loading('正在删除');
    if (!selectedRows) return true;
    try {
      await deleteInterfaceInfoUsingPost({
        id: selectedRows.id,
      });
      hide();
      message.success('删除成功！即将刷新！');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('删除失败！请重试！');
      return false;
    }
  };

  /**
   * 下线接口
   * @param selectedRows
   */
  const handleOffline = async (selectedRows: API.IdRequest) => {
    const hide = message.loading('正在下线接口');
    if (!selectedRows) return true;
    try {
      await offlineInterfaceInfoUsingPost({
        id: selectedRows.id,
      });
      hide();
      message.success('接口删除成功，即将刷新！');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('删除失败，请重试！');
      return false;
    }
  };
  /**
   * @en-US Update node
   * @zh-CN 更新节点
   *
   * @param fields
   */
  const handleUpdate = async (fields: API.InterfaceInfo) => {
    const hide = message.loading('修改中！');
    try {
      await updateInterfaceInfoUsingPost({
        ...fields,
        id: currentRow?.id ,
      });
      hide();
      message.success('修改成功！');
      return true;
    } catch (error: any) {
      hide();
      message.error('修改失败，' + error.message);
      return false;
    }

  };
  const columns: ProColumns<API.InterfaceInfo>[] = [
    {
    title: 'id号',
    dataIndex: 'id',
    valueType: 'index',
    },
      {
      title: '接口名称',
      dataIndex: 'name',
      valueType: 'text',
  },{
        title: '描述',
        //description对应后端的字段名
        dataIndex: 'description',
        // 展示的文本为富文本编辑器
        valueType: 'textarea',
      },
      {
        title: '请求方法',
        dataIndex: 'method',
        // 展示的文本为富文本编辑器
        valueType: 'text',
      },
      {
        title: 'url',
        dataIndex: 'url',
        valueType: 'text',
      },
      {
        title: '请求头',
        dataIndex: 'requestHeader',
        valueType: 'jsonCode',
      },
      {
        title: '响应头',
        dataIndex: 'responseHeader',
        valueType: 'jsonCode',
      },
      {
        title: '状态',
        dataIndex: 'status',
        hideInForm: true,
        valueEnum: {
          0: {
            text: '关闭',
            status: 'Default',
          },
          1: {
            text: '开启',
            status: 'Processing',
          },
        },
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
        valueType: 'dateTime',
        hideInForm: true,
      },
      {
        title: '更新时间',
        dataIndex: 'updateTime',
        valueType: 'dateTime',
        hideInForm: true,
      },
      {
        title: '操作',
        dataIndex: 'option',
        valueType: 'option',
        render: (_, record) => [
          <a
            key="config"
            onClick={() => {
              handleUpdateModalOpen(true);
              setCurrentRow(record);
            }}
          >
            修改
          </a>,
          <Button
            key="delete"
            type={"text"}
            danger={true}
            onClick={() => {
              handleRemove(record);
            }}
          >
            删除
          </Button>,
         record.status === 0 ? <Button
            key="publish"
            type={"primary"}
            onClick={() => {
              handleOnline(record);
            }}
          >
            发布
          </Button> : null,
          record.status === 1 ? <Button
            key="offline"
            type="text"
            onClick={() => {
              handleOffline(record);
            }}
          >
            下线
          </Button> : null,
        ],
      },
    ]
  return (
    <PageContainer>
      <ProTable<API.RuleListItem, API.PageParams>
        headerTitle={'查询表单'}
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="key"
            onClick={() => {
              handleModalOpen(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request = {async(params: API.listInterfaceInfoByPageUsingGETParams ) => {
          const res = await listInterfaceInfoByPageUsingGet({
            ...params
          })
          if (res?.data) {
            return  {
              data: res?.data.records || [],
              success: true,
              total: res.data.total,
            }
          }
        }}

      columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              Chosen{' '}
              <a
                style={{
                  fontWeight: 600,
                }}
              >
                {selectedRowsState.length}
              </a>{' '}
              项 &nbsp;&nbsp;
              <span>
                Total number of service calls{' '}
                {selectedRowsState.reduce((pre, item) => pre + item.callNo!, 0)} 万
              </span>
            </div>
          }
        >
          <Button
            onClick={async () => {
              await handleRemove(selectedRowsState);
              setSelectedRows([]);
              actionRef.current?.reloadAndRest?.();
            }}
          >
           批量删除
          </Button>
          <Button type="primary">Batch approval</Button>
        </FooterToolbar>
      )}
      {/* 创建一个CreateModal组件，用于在点击新增按钮时弹出 */}
      <CreateModal
        columns={columns}
        // 当取消按钮被点击时,设置更新模态框为false以隐藏模态窗口
        onCancel={() => {
          handleModalOpen(false);
        }}
        // 当用户点击提交按钮之后，调用handleAdd函数处理提交的数据，去请求后端添加数据(这里的报错不用管,可能里面组件的属性和外层的不一致)
        onSubmit={(values) => {
          handleAdd(values);
        }}
        // 根据更新窗口的值决定模态窗口是否显示
        visible={createModalOpen}
      />

      <UpdateModal
        columns={columns}
        onSubmit={async (value) => {
          const success = await handleUpdate(value);
          if (success) {
            handleUpdateModalOpen(false);
            setCurrentRow(undefined);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          handleUpdateModalOpen(false);
          if (!showDetail) {
            setCurrentRow(undefined);
          }
        }}
        visible={updateModalOpen}
        values={currentRow || {}}
      />

      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions<API.RuleListItem>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<API.RuleListItem>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};
export default TableList;
