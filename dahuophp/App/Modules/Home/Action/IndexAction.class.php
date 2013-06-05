<?php
// +----------------------------------------------------------------------
// | TOPThink [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2011 http://topthink.com All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------
// $Id$
header ( "Access-Control-Allow-Origin:*" );

class IndexAction extends Action {
	public function index() {
		switch ($_SERVER ['REQUEST_METHOD']) {
			case 'GET' :
				$the_request = &$_GET;
				break;
			case 'POST' :
				$the_request = &$_POST;
				break;
			case 'PUT' :
				$the_request = &$_PUT;
				break;
			case 'HEAD' :
				$the_request = &$_HEAD;
				break;
			default :
		}
		//dump('$_SERVERREQUEST_METHOD='.$_SERVER['REQUEST_METHOD'].' \n');		
		$do = $the_request ['do'];
		$callback = $_REQUEST ['callback'];
		switch ($do) {
			case 'login' :
				$data = $this->login ( $the_request );
				if(!$data){
					$data['result']	='false';
				}else {
					$data['result']	='true';
				}						
				echo json_encode ( $data );
				break;
			case 'register' :
				$data = $this->register ( $the_request );
				echo json_encode ( $data );
				break;
			case 'savemsg' :
				$data['result'] = $this->savemsg ( $the_request );
				echo json_encode ( $data );
				break;
			case 'getregion' :
				$data = $this->getregion ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'getmsglist' :
				$data = $this->getmsglist ( $the_request );
				echo json_encode ( $data );
				break;
			default :
				$sql = 'select * from region';
				$data = M ( 'region' )->query ( $sql );
				print (json_encode ( $data )) ;
				break;
		}
	
	}
	function object2array($object) {
		if (is_object ( $object )) {
			foreach ( $object as $key => $value ) {
				$array [$key] = $value;
			}
		} else {
			$array = $object;
		}
		return $array;
	}
	//登录用户
	public function login($the_request) {				
		$map ['mobile'] = $the_request ['mobile'];		
		$user = M ( 'users' )->where ( $map )->find ();		
		return $user;		
	}
	//注册用户
	public function register($the_request) {
		$maps ['mobile'] = $the_request ['mobile'];		
		$userexits = M ( 'users' )->where ( $maps )->select ();		
		if ($userexits) {
			//已经存在为-1
			$result = $userexits;
			$result ['result'] = - 1;
		} else {
			$data ['mobile'] = $the_request ['mobile'];
			$data ['password'] = md5 ( $the_request ['password'] );
			$data ['name'] = $the_request ['name'];
			$data ['type'] = $the_request ['type'];
			$data ['regdate'] = date ( "Y-m-d H:i:s", time () );
			
			$re = M ( 'users' )->add ( $data );			
			if ($re) {
				$map ['id'] = $re;
				$result = M ( 'users' )->where ( $map )->find ();				
				$res = M ( 'register' )->add ( $data );
				$result ['result'] = $re;
			}
		}
		return $result;
	
	}
	//发布信息
	public function savemsg($the_request) {
		$map["startadd"] = $the_request ['startadd'];
		$map["endadd"] = $the_request ['endadd'];
		$map["deadline"] = $the_request ['deadline'];
		$map["remark"] = $the_request ['remark'];				
		$map["weight"] = $the_request ['weight'];		
		$re = D ( 'Messages' )->add ( $map );			
		return $re;
	}
	public function getmsglist($the_request) {
		if($the_request ['startadd'])
		$map["startadd"] = $the_request ['startadd'];
		if($the_request ['endadd'])
		$map["endadd"] = $the_request ['endadd'];
		$user = M ( 'messages' )->where($map)->select ();
		return $user;
	}
}