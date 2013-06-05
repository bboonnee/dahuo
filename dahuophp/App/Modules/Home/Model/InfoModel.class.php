<?php
class InfoModel extends Model {
    public function getList($count=5){
        return $this->order('info_id DESC')->field(true)->select();
    }

    public function getDetail($info_id=0){
        return $this->field(true)->find($info_id);
    }
}