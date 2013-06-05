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
		
		$content = $this->Array2String ( $the_request );
		
		$this->log ( $do, $the_request ['account'], $name, $content, $the_request ['device'] );
		switch ($do) {
			case 'login' :
				$map ["mobile"] = trim ( $the_request ['mobile'] );
				$password = M ( 'mba_user' )->where ( $map )->find ();
				print (json_encode ( $password )) ;
				break;
			case 'loginin' :
				$map ["mobile"] = trim ( $the_request ['mobile'] );
				$pass = trim ( $the_request ['password'] );
				$data = M ( 'mba_user' )->where ( $map )->find ();
				if (md5 ( $pass ) == $data ['password']) {
					$this->log ( $do, $data ['account'], $data ['name'], $data ['mobile'], $the_request ['device'] );
				} else {
					$data = "{}";
				}
				//print (json_encode ( $password )) ;
				//	start output
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			
			case 'register' :
				$data ["name"] = $this->register ( $the_request );
				//print (json_encode ( $data )) ;
				//	start output
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'getuserlist' :
				$sql = 'select * from mba_user order by weight desc limit 30';
				$data = M ( 'Mba_user' )->query ( $sql );
				print (json_encode ( $data )) ;
				break;
			case 'getmaplist' :
				$data = $this->getmaplist ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			
			case 'searchuser' :
				$search = trim ( $the_request ['keyword'] );
				$field = trim ( $the_request ['field'] );
				$limit = trim ( $the_request ['limit'] );
				if ($limit == NULL)
					$limit = 100;
				if ($field == NULL) {
					$sql = "select * from mba_user where name like '%" . $search . "%' or company like '%" . $search . "%' or classno like '%" . $search . "%' or mobile like '%" . $search . "%' or account like '%" . $search . "%' order by weight desc limit " . $limit;
				} else {
					$sql = "select * from mba_user where " . $field . " like '%" . $search . "%'  order by weight desc limit " . $limit;
				}
				$data = M ( 'Mba_user' )->query ( $sql );
				//start output
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				//print (json_encode ( $data )) ;
				//record the search
				$data ['search_user'] = trim ( $the_request ['userid'] );
				$data ['keyword'] = trim ( $the_request ['keyword'] );
				$data ['searchtime'] = date ( "Y-m-d H:i:s", time () );
				$searchid = M ( 'search' )->add ( $data );
				break;
			case 'getclasslist' :
				$sql = 'select distinct classno,startyear from mba_user order by classno';
				$data = M ( 'Mba_user' )->query ( $sql );
				//print (json_encode ( $data )) ;
				//	start output
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				//print (json_encode ( $data )) ;
				break;
			case 'getkeywordlist' :
				$sql = 'select   keyword,count(keyword) as num from search group by keyword order by num desc limit 10';
				$data = M ( 'search' )->query ( $sql );
				print (json_encode ( $data )) ;
				break;
			case 'getuserinfo' :
				$id = trim ( $the_request ['userid'] );
				$sql = 'select  info.info_id as info_id , name,data,input_style,info_group from user_info,info  where user_info.info_id = info.info_id and user_id = "+$id+" order by info_id';
				$data = M ( 'info' )->query ( $sql );
				print (json_encode ( $data )) ;
				break;
			case 'getinfolist' :
				$id = trim ( $the_request ['userid'] );
				$sql = 'select * from info';
				$data = M ( 'info' )->query ( $sql );
				print (json_encode ( $data )) ;
				break;
			case 'checkreg' :
				$data = $this->checkreg ( $the_request );
				if (! $data) {
					$data = "{}";
				} else {
					$data = $this->register ( $the_request );
				}
				//print (json_encode ( $data )) ;
				//	start output
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'regcheck' :
				$data = $this->regcheck ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'regchecknewuser' :
				$data = $this->regchecknewuser ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			
			case 'updateuserinfo' :
				//$data = $this->updateUserInfo ( $the_request );
				$data = $this->updateInfos ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				
				break;
			case 'updatepic' :
/*				$sql = "UPDATE mba_user SET picture='".$the_request['account']."' where account='".$the_request['account']."'";
				$data= M ( 'mba_user' )->query ( $sql );*/
				$map ['picture'] = $the_request ['account'];
				$data = M ( 'mba_user' )->where ( 'account="' . $the_request ['account'] . '"' )->save ( $map );
				$data = M ( 'mba_user' )->where ( 'account="' . $the_request ['account'] . '"' )->find ();
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $res );
				}
				break;
			case 'addsuggestion' :
				$data = $this->addSuggestion ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			
			case 'getpost' :
				$sql = 'select * from post  where status = 0 order by posttime desc limit 50';
				$data = M ( 'post' )->query ( $sql );
				//dump($data);
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'savepost' :
				//$data = $this->updateUserInfo ( $the_request );				
				$data = $this->savePost ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				
				break;
			case 'getadlist' :
				$sql = 'select * from adcompany';
				$data = M ( 'adcompany' )->query ( $sql );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'getwppost' :
				$data = $this->getWpPost ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'regmail' :
				$data ["name"] = $this->regmail ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'verifycheck' :
				$data = $this->verifycheck ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'favor' :
				$data ['result'] = $this->favor ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'getfavorlist' :
				$data = $this->getfavorlist ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'getmyclass' :
				$data = $this->getmyclass ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'sendregmail' :
				$data ['result'] = $this->sendregmail ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			case 'changepassword' :
				$data ['result'] = $this->changepassword ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			//
			case 'sendComment' :
				$data = $this->sendComment ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				
				break;
			case 'getcommentlist' :
				$data = $this->getcommentlist ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				
				break;
			case 'deletepost' :
				$data = $this->deletepost ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				
				break;
			case 'deletecomment' :
				$data = $this->deleteComment ( $the_request );
				if ($callback) {
					header ( 'Content-Type: text/javascript' );
					echo $callback . '(' . json_encode ( $data ) . ');';
				} else {
					header ( 'Content-Type: application/x-json' );
					echo json_encode ( $data );
				}
				break;
			
			default :
				
				if (strpos ( $the_request ['account'], '@', 0 )) {
					$to = substr ( $the_request ['account'], 0, strpos ( $the_request ['account'], "@" ) );
				} else {
					$to = $the_request ['account'] . '@sem.tsinghua.edu.cn';
				}
				$to = $to . '@sem.tsinghua.edu.cn';
				dump ( $to );
				
				/*$sql = 'select distinct classno,startyear from mba_user order by classno';
				$data = M ( 'Mba_user' )->query ( $sql );
				print (json_encode ( $data )) ;*/
				break;
		}
	
	}
	//登录用户
	public function login($the_request) {
		
		$mobile = $the_request ['mobile'];
		$pass = $the_request ['password'];
		
		if ((! $mobile) || (! $pass)) {
			echo "Login name or password is empty!";
		}
		$map ['mobile'] = $mobile;
		$user = M ( 'mba_user' )->where ( $map )->find ();
		if (md5 ( $pass ) != $user ['password']) {
			
			echo "Password is wrong \n " . $email . $pass . ' \n';
			echo $user ['password'] . "\n";
			dump ( md5 ( $pass ) );
			return NULL;
		} else {
			return $user;
		}
	}
	//核对信息
	public function checkreg($the_request) {
		
		//$map ['mobile'] = $the_request ['mobile'];
		//$map ['password'] = $the_request ['password'];		
		$name = $the_request ['name'];
		$account = $the_request ['account'];
		$classno = $the_request ['classno'];
		//$map ['studentno'] = $the_request ['studentno'];
		$sqls = "select * from mba_user where  name='" . $name . "' and account='" . $account . "' and classno='" . $classno . "'";
		$data = M ( 'mba_user' )->query ( $sqls );
		return $data;
	}
	//注册用户
	public function register($the_request) {
		$mobile = $the_request ['mobile'];
		$password = md5 ( $the_request ['password'] );
		$updatetime = date ( "Y-m-d H:i:s", time () );
		$weight = "2";
		$account = $the_request ['account'];
		$permissions = $the_request ['permissions'];
		
		$sql = "UPDATE mba_user SET mobile='" . $mobile . "',password='" . $password . "',updatetime='" . $updatetime . "',permissions='" . $permissions . "',weight='" . $weight . "' WHERE account='" . $account . "'";
		$re = M ( 'mba_user' )->query ( $sql );
		
		$data ['mobile'] = $the_request ['mobile'];
		$data ['password'] = md5 ( $the_request ['password'] );
		$data ['name'] = $the_request ['name'];
		$data ['account'] = $the_request ['account'];
		$data ['studentno'] = $the_request ['studentno'];
		$data ['applytime'] = date ( "Y-m-d H:i:s", time () );
		$data ['status'] = "0";
		$res = M ( 'register' )->add ( $data );
		if ($res) {
			$map ['id'] = $res;
			$result = M ( 'mba_user' )->where ( $map )->find ();
			return $result;
		}
	
	}
	//更新记录
	public function updateUserInfo($the_request) {
		$data ['sendid'] = $the_request ['sendid'];
		$data ['account'] = $the_request ['account'];
		
		$data ['city'] = $the_request ['city'];
		$data ['classno'] = $the_request ['classno'];
		$data ['company'] = $the_request ['company'];
		$data ['email'] = $the_request ['email'];
		$data ['mobile'] = $the_request ['mobile'];
		$data ['title'] = $the_request ['title'];
		$data ['uid'] = $the_request ['uid'];
		$data ['name'] = $the_request ['uname'];
		$data ['gender'] = $the_request ['gender'];
		$data ['status'] = "0";
		$data ['createtime'] = date ( "Y-m-d H:i:s", time () );
		
		$res = M ( 'info_update' )->add ( $data );
		
		if ($the_request ['sendid'] == $the_request ['uid']) {
			if ((strlen ( $the_request ['mobile'] )) > 0)
				$update ["mobile"] = $the_request ['mobile'];
			if ((strlen ( $the_request ['telephone'] )) > 0)
				$update ["telephone"] = $the_request ['telephone'];
			if ((strlen ( $the_request ['email'] )) > 0)
				$update ["email"] = $the_request ['email'];
			
			if ((strlen ( $the_request ['company'] )) > 0)
				$update ["company"] = $the_request ['company'];
			if ((strlen ( $the_request ['title'] )) > 0)
				$update ["title"] = $the_request ['title'];
			if ((strlen ( $the_request ['city'] )) > 0)
				$update ["city"] = $the_request ['city'];
			if ((strlen ( $the_request ['address'] )) > 0)
				$update ["address"] = $the_request ['address'];
			
			if ((strlen ( $the_request ['college'] )) > 0)
				$update ["college"] = $the_request ['college'];
			
			if ((strlen ( $the_request ['permissions'] )) > 0)
				$update ["permissions"] = $the_request ['permissions'];
			
			$update ["id"] = $the_request ['sendid'];
			$res = M ( 'mba_user' )->save ( $update );
			$res = M ( 'mba_user' )->where ( 'id=' . $update ["id"] )->find ();
		}
		return $res;
	}
	//更新记录
	public function updateInfos($the_request) {
		$jsondata = stripslashes ( $the_request ['data'] );
		$array = json_decode ( $jsondata );
		$arr = $this->object2array ( $array );
		//print_r($arr);				
		foreach ( $arr as $key => $value ) {
			if ($value)
				$data [$key] = $value;
		}
		//print_r($data);		
		$res = M ( 'info_update' )->add ( $data );
		//print_r($data ["id"] );
		$info = M ( 'mba_user' )->where ( 'id=' . $data ["id"] )->find ();
		$data ['updatetime'] = date ( "Y-m-d H:i:s", time () );
		//dump($info);		
		if (! $info) {
			$res = M ( 'mba_user' )->add ( $data );
		} else {
			$res = M ( 'mba_user' )->save ( $data );
		}
		$res = M ( 'mba_user' )->where ( 'id=' . $data ["id"] )->find ();
		$this->log ( 'updateInfos', $res ['account'], $res ['name'], $jsondata );
		return $res;
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
	function Array2String($Array) {
		$Return = '';
		$NullValue = "^^^";
		foreach ( $Array as $Key => $Value ) {
			
			if (is_array ( $Value ))
				$ReturnValue = '^^array^' . Array2String ( $Value );
			else
				$ReturnValue = (strlen ( $Value ) > 0) ? $Value : $NullValue;
			$Return .= $Key . '=' . $ReturnValue . '&';
		
		//$Return .= urlencode ( iconv('UTF-8', 'GB2312', $Key)  ) . '|' . urlencode (  $ReturnValue ) . '||';			
		}
		//return urlencode ( substr ( $Return, 0, - 2 ) );
		return substr ( $Return, 0, - 1 );
	}
	
	//增加建议
	public function addSuggestion($the_request) {
		$data ['user_id'] = $the_request ['uid'];
		$data ['content'] = $the_request ['content'];
		$data ['time'] = date ( "Y-m-d H:i:s", time () );
		$res = M ( 'suggestion' )->add ( $data );
		$data ['name'] = $res;
		//dump($data);
		return $data;
	}
	public function log($do, $uid, $name, $content, $device) {
		$log ["content"] = $content;
		$log ["event"] = $do;
		$log ["user_id"] = $uid;
		$log ["name"] = $name;
		$log ["device"] = $device;
		$log ["logtime"] = date ( "Y-m-d H:i:s", time () );
		$logid = M ( 'log' )->add ( $log );
	}
	public function regcheck($the_request) {
		$jsondata = stripslashes ( $the_request ['data'] );
		$array = json_decode ( $jsondata );
		$arr = $this->object2array ( $array );
		foreach ( $arr as $key => $value ) {
			if (($value) && ($key != 'id'))
				$data [$key] = $value;
		}
		
		$map ['account'] = $data ['account'];
		$map ['classno'] = $data ['classno'];
		$map ['name'] = $data ['name'];
		//print_r($data);
		//print_r($map);
		$content = $map ['account'] . ',' . $map ['classno'] . ',' . $map ['name'] . ',' . $data ['mobile'];
		$this->log ( 'regcheck', $map ['account'], $map ['name'], $content, $the_request ['device'] );
		$res = M ( 'mba_user' )->where ( $map )->find ();
		//print_r($res);
		if ($res) {
			$data ['id'] = $res ['id'];
			$data ['updatetime'] = date ( "Y-m-d H:i:s", time () );
			$data ['password'] = md5 ( $data ['password'] );
			$save = M ( 'mba_user' )->save ( $data );
			
			$res ['mobile'] = $data ['mobile'];
			$res ['permissions'] = $data ['permissions'];
			
			$regi ['applytime'] = date ( "Y-m-d H:i:s", time () );
			$regi ['status'] = "0";
			$regi ['password'] = $data ['password'];
			$regi ['mobile'] = $data ['mobile'];
			$regi ['name'] = $data ['name'];
			$regi ['account'] = $data ['account'];
			
			//print_r($data);
			$regi = M ( 'register' )->add ( $regi );
		
		//dump($regi);	
		} else {
			$res = '{}';
		}
		
		return $res;
	}
	public function regchecknewuser($the_request) {
		$jsondata = stripslashes ( $the_request ['data'] );
		$array = json_decode ( $jsondata );
		$arr = $this->object2array ( $array );
		foreach ( $arr as $key => $value ) {
			if (($value) && ($key != 'id'))
				$data [$key] = $value;
		}
		
		$map ['classno'] = $data ['classno'];
		$map ['name'] = $data ['name'];
		//print_r($data);
		//print_r($map);
		$content = $data ['account'] . ',' . $map ['classno'] . ',' . $map ['name'] . ',' . $data ['mobile'];
		$this->log ( 'regchecknewuser', $data ['account'], $map ['name'], $content, $the_request ['device'] );
		$res = M ( 'mba_user' )->where ( $map )->find ();
		//print_r($res);
		if ($res) {
			$data ['id'] = $res ['id'];
			$data ['updatetime'] = date ( "Y-m-d H:i:s", time () );
			$data ['password'] = md5 ( $data ['password'] );
			$save = M ( 'mba_user' )->save ( $data );
			
			$res ['mobile'] = $data ['mobile'];
			$res ['permissions'] = $data ['permissions'];
			
			$regi ['applytime'] = date ( "Y-m-d H:i:s", time () );
			$regi ['status'] = "0";
			$regi ['password'] = $data ['password'];
			$regi ['mobile'] = $data ['mobile'];
			$regi ['name'] = $data ['name'];
			$regi ['account'] = $data ['account'];
			//print_r($data);
			$regi = M ( 'register' )->add ( $regi );
		} else {
			$res = '{}';
		}
		return $res;
	}
	//更新post记录
	//更新post记录
	public function savePost($the_request) {
		$jsondata = stripslashes ( $the_request ['data'] );
		$array = json_decode ( $jsondata );
		$arr = $this->object2array ( $array );
		//print_r($arr);				
		foreach ( $arr as $key => $value ) {
			if ($value)
				$data [$key] = $value;
		}
		if (($data ['otherauthor'] != NULL) && ($data ['otherauthorid'] != NULL)) {
			$data ['author'] = $data ['otherauthor'];
			$data ['author_id'] = $data ['otherauthorid'];
		}
		$info = M ( 'post' )->where ( 'id=' . $data ["id"] )->find ();
		if (! $info) {
			$data ['posttime'] = date ( "Y-m-d H:i:s", time () );
			$res = M ( 'post' )->add ( $data );
		} else {
			$data ['modifytime'] = date ( "Y-m-d H:i:s", time () );
			$res = M ( 'post' )->save ( $data );
		}
		$result = M ( 'post' )->where ( 'id=' . $res )->find ();
		$this->log ( 'savePostlog', $data ['author_id'], $data ['author'], $data ['title'], $the_request ['device'] );
		$result ['content'] = "";
		return $result;
	}
	//delete post
	public function deletePost($the_request) {
		$data ['id'] = $the_request ["postid"];
		$data ['modifytime'] = date ( "Y-m-d H:i:s", time () );
		$data ['status'] = '4';
		$res = M ( 'post' )->save ( $data );
		$this->log ( 'deletePost', $data ['author_id'], $data ['author'], $data ['title'], $the_request ['device'] );
		if ($res) {
			return $data;
		} else {
			return $res;
		}
	}
	public function deleteComment($the_request) {
		$data ['id'] = $the_request ["commentid"];
		$data ['modifytime'] = date ( "Y-m-d H:i:s", time () );
		$data ['status'] = '4';
		$res = M ( 'Comments' )->save ( $data );
		$this->log ( 'deleteComment', $data ['id'], $data ['account'], $data ['modifytime'], $the_request ['device'] );
		if ($res) {
			return $data;
		} else {
			return $res;
		}
	}
	
	public function savePostold($the_request) {
		
		$data ['id'] = $the_request ['id'];
		$data ['author_id'] = $the_request ['account'];
		$data ['author'] = $the_request ['author'];
		$data ['catalog'] = $the_request ['catalog'];
		$data ['title'] = $the_request ['title'];
		$data ['type'] = $the_request ['type'];
		$data ['content'] = $the_request ['content'];
		$data ['posttime'] = date ( "Y-m-d H:i:s", time () );
		dump ( $data );
		$res = M ( 'post' )->add ( $data );
		$result = M ( 'post' )->where ( 'id=' . $res )->find ();
		$this->log ( 'savePostlog', $result ['author_id'], $result ['author'], $result ['title'], $the_request ['device'] );
		$result ['content'] = "";
		return $result;
	}
	//
	public function getWpPost($the_request) {
		$sql = "select id,post_content,post_date,post_title from wp_posts where id=" . $the_request ['post_id'];
		$data = M ( 'wp_posts' )->query ( $sql );
		return $data;
	}
	//
	public function regmail($the_request) {
		$account = $the_request ['account'];
		if (strpos ( $account, '@', 0 )) {
			$account = substr ( $account, 0, strpos ( $account, "@" ) );
		}
		$data ['account'] = $account;
		$data ['createtime'] = date ( "Y-m-d H:i:s", time () );
		$data ['verifycode'] = substr ( time (), - 4, 4 );
		$map ['account'] = $account;
		$veri = M ( 'verify' )->where ( $map )->find ();
		$t1 = strtotime ( $veri ['createtime'] );
		if (time () - $t1 > 1000) {
			if ($veri) {
				$res = M ( 'verify' )->save ( $data );
			} else {
				$res = M ( 'verify' )->add ( $data );
			}
			$to = $account . '@sem.tsinghua.edu.cn';
			$subject = "mbarenmai.com 验证码";
			$message = " <html> <head> <title>HTML email</title> </head> <body> <p>欢迎注册MBA人脉</p> <table> <tr> <th>你的验证码是</th> <th>" . $data ['verifycode'] . "</th> </tr> </table> </body> </html> ";
			// 当发送 HTML 电子邮件时，请始终设置 content-type$headers = "MIME-Version: 1.0" . "\r\n";
			$headers .= "Content-type:text/html;charset=utf8" . "\r\n"; // 更多报头
			$headers .= 'From: yib.11@sem.tsinghua.edu.cn' . "\r\n";
			$headers .= 'Cc: ' . "\r\n";
			set_time_limit ( 0 );
			$re = mail ( $to, $subject, $message, $headers );
			$this->log ( 'sendVerify', $data ['account'], $re, $data ['createtime'], $the_request ['device'] );
		}
		return $res;
	}
	//
	public function verifycheck($the_request) {
		$map ['account'] = $the_request ['account'];
		$map ['verifycode'] = $the_request ['code'];
		$res = M ( 'verify' )->where ( $map )->find ();
		$this->log ( 'verifycheck', $data ['account'], $res, $data ['createtime'], $the_request ['device'] );
		if ($res) {
			$data ['account'] = $map ['account'];
			$ress = M ( 'mba_user' )->where ( $data )->find ();
			if ($ress)
				$res = $ress;
		}
		;
		return $res;
	}
	//
	public function favor($the_request) {
		$this->log ( 'favor', $data ['uid'], $res, $data ['favoruid'], $the_request ['device'] );
		$favorPressed = $the_request ['favorPressed'];
		$map ['user_id'] = $the_request ['uid'];
		$map ['favor_uid'] = $the_request ['favoruid'];
		$res = M ( 'favor' )->where ( $map )->find ();
		if ($favorPressed == 'true') {
			//增加收藏
			if (! $res) {
				$result = M ( 'favor' )->add ( $map );
			}
		} else {
			//删除收藏			
			$result = M ( 'favor' )->where ( $res )->delete ();
		}
		return $result;
	}
	//
	public function getfavorlist($the_request) {
		$uid = trim ( $the_request ['uid'] );
		if ($limit == NULL)
			$limit = 100;
		if ($uid) {
			$sql = "select mba_user.*,isFavorite from mba_user, favor where mba_user.id= favor.favor_uid and favor.user_id = " . $uid . " order by weight desc limit " . $limit;
			//dump($sql);
			$data = M ( 'Mba_user' )->query ( $sql );
		}
		if (sizeof ( $data ) < 1) {
			$sql = "select * from mba_user where mba_user.classno =
		(select classno from mba_user where id = " . $uid . " ) order by weight desc limit " . $limit;
			$data = M ( 'Mba_user' )->query ( $sql );
		
		}
		return $data;
	}
	//
	public function getmyclass($the_request) {
		$uid = trim ( $the_request ['uid'] );
		$limit = trim ( $the_request ['limit'] );
		$sql = "select * from mba_user where mba_user.classno =
		(select classno from mba_user where id = " . $uid . " ) order by weight desc limit " . $limit;
		$data = M ( 'Mba_user' )->query ( $sql );
		//dump($sql);	
		return $data;
	}
	//
	public function sendregmail($the_request) {
		$password = substr ( time (), - 6, 6 );
		//dump($password);	
		$account = $the_request ['account'];
		if (strpos ( $account, '@', 0 )) {
			$account = substr ( $account, 0, strpos ( $account, "@" ) );
		}
		
		$to = $account . '@sem.tsinghua.edu.cn';
		
		$map ['account'] = $account;
		$res = M ( 'mba_user' )->where ( $map )->find ();
		//print_r($res);
		if ($res) {
			$data ['id'] = $res ['id'];
			$data ['updatetime'] = date ( "Y-m-d H:i:s", time () );
			$data ['password'] = md5 ( $password );
			$data ['mobile'] = $the_request ['mobile'];
			$save = M ( 'mba_user' )->save ( $data );
			
			$res ['mobile'] = $data ['mobile'];
			$res ['permissions'] = $data ['permissions'];
			
			$regi ['applytime'] = date ( "Y-m-d H:i:s", time () );
			$regi ['status'] = "0";
			$regi ['password'] = $data ['password'];
			$regi ['mobile'] = $res ['mobile'];
			$regi ['name'] = $res ['name'];
			$regi ['account'] = $res ['account'];
			//print_r($data);
			$regi = M ( 'register' )->add ( $regi );
			
			$subject = "www.mbarenmai.com Register Mail";
			$message = " <html> <head> <title>HTML email</title> </head> <body> <p>欢迎注册MBA人脉</p>
			 <table> <tr> <th>你的账号是</th> <th>" . $the_request ['mobile'] . "</th> </tr> 
			 <tr> <th>登录密码是</th> <th>" . $password . "</th> </tr>			 
			 </table> </body> </html> ";
			// 当发送 HTML 电子邮件时，请始终设置 content-type$headers = "MIME-Version: 1.0" . "\r\n";
			$headers .= "Content-type:text/html;charset=utf8" . "\r\n"; // 更多报头
			$headers .= 'From: yib.11@sem.tsinghua.edu.cn' . "\r\n";
			$headers .= 'Cc: ' . "\r\n";
			set_time_limit ( 0 );
			$ress = mail ( $to, $subject, $message, $headers );
			$this->log ( 'sendregmail', $account, $res ['name'], $message, $the_request ['device'] );
		
		//dump($regi);	
		} else {
			
			$subject = "www.mbarenmai.com Register Mail";
			$message = " <html> <head> <title>HTML email</title> </head> <body> <p>欢迎注册MBA人脉</p>
			 <table> <tr> <th>亲爱的" . $account . "@sem.tsinghua.edu.cn:</th> </tr>
			 <tr><th>您的账户信息暂时未存在于我们MBA人脉的资料库中</th> </tr> 
			 <tr> <th>麻烦您回复本邮件，附上您的姓名，入学年份，班级，经管账号，注册手机，公司名称，职位等信息，我们将手工为您添加账户</th> </tr>			 
			 <tr> <th>MBA人脉管理员 : yib.11</th> </tr>
			 </table> </body> </html> ";
			// 当发送 HTML 电子邮件时，请始终设置 content-type$headers = "MIME-Version: 1.0" . "\r\n";
			$headers .= "Content-type:text/html;charset=utf8" . "\r\n"; // 更多报头
			$headers .= 'From: yib.11@sem.tsinghua.edu.cn' . "\r\n";
			$headers .= 'Cc: ' . "\r\n";
			set_time_limit ( 0 );
			$ress = mail ( $to, $subject, $message, $headers );
			$this->log ( 'sendregmail', $account, $ress, $message, $the_request ['device'] );
		}
		return $ress;
	}
	//
	public function changepassword($the_request) {
		$map ["account"] = trim ( $the_request ['account'] );
		$password = trim ( $the_request ['password'] );
		$res = M ( 'Mba_user' )->where ( $map )->find ();
		if ($res) {
			$data ['id'] = $res ['id'];
			$data ['updatetime'] = date ( "Y-m-d H:i:s", time () );
			$data ['password'] = md5 ( $password );
			$result = M ( 'mba_user' )->save ( $data );
		} else {
			//$result= "{}";
		}
		//dump($sql);	
		return $result;
	}
	//更新记录
	public function getmaplist($the_request) {
		
		$map ['account'] = $the_request ["account"];
		$data ['recenttime'] = time ();
		$data ['latitude'] = $the_request ['latitude'];
		$data ['longitude'] = $the_request ['longitude'];
		$saveres = M ( 'mba_user' )->where ( $map )->save ( $data );
		//$this->log ( 'getmaplist', $res ['account'], $res ['name'], $jsondata );		
		$sql = 'select * from mba_user where latitude is not null and longitude is not null and account<>"' . $the_request ['account'] . '" order by recenttime desc limit 30';
		$res = M ( 'Mba_user' )->query ( $sql );
		return $res;
	}
	//更新记录
	public function getcommentlist($the_request) {
		$map ['postid'] = $the_request ["postid"];
		$map ['status'] = '0';
		$res = M ( 'Comments' )->where ( $map )->select ();
		return $res;
	}
	public function sendComment($the_request) {
		$data ['postid'] = $the_request ["postid"];
		$data ['author'] = $the_request ["author"];
		$data ['authorurl'] = $the_request ["account"];
		$data ['account'] = $the_request ["account"];
		$data ['date'] = date ( "Y-m-d H:i:s", time () );
		$data ['content'] = $the_request ["content"];
		$data ['parent'] = $the_request ["parent"];
		$add ['id'] = M ( 'comments' )->add ( $data );
		$res = M ( 'comments' )->where ( $add )->find ();
		//$this->sendCommentmail($the_request);
		return $res;
	}
	public function sendCommentmail($the_request) {
		$map ['id'] = $the_request ["postid"];
		$post = M ( 'post' )->where ( $map )->find ();		
		if($post){
			$to = $post[author_id] . '@sem.tsinghua.edu.cn';
			$subject = "You got reply from MBARenMai.com";
			$message = " <html> <head> <title>HTML email</title> </head> <body> 
			<p>您在MBA人脉的帖子<" . $post ['title'] . ">收到了来自 " . $the_request ['author']."(". $the_request ['account']. " )的回复</p>
			<table> <tr> <th>" . $the_request ['content'] . "</th> </tr> <tr> <th>" . date ( "Y-m-d H:i:s", time () ) . "</th> </tr></table> </body> </html> ";
			// 当发送 HTML 电子邮件时，请始终设置 content-type$headers = "MIME-Version: 1.0" . "\r\n";
			$headers .= "Content-type:text/html;charset=utf8" . "\r\n"; // 更多报头
			$headers .= 'From: yib.11@sem.tsinghua.edu.cn' . "\r\n";
			$headers .= 'Cc: ' . "\r\n";
			set_time_limit ( 0 );
			$re = mail ( $to, $subject, $message, $headers );
			$this->log ( 'sendCommentmail', $the_request ['account'], $re, $message, $the_request ['device'] );					
		}

	}
}