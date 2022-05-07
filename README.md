# Paper Agent
UDP 소켓 통신을 통해 UDP 통신이 가능한 모든 프로세스와 페이퍼 서버를 연결할 수 있습니다.

## Object Types
* \*는 필수 필드입니다.
### position
#### Fields
##### x(double, *)
X 좌표를 나타냅니다.
##### y(double, *)
Y 좌표를 나타냅니다.
##### z (double, *)
Z 좌표를 나타냅니다.
##### yaw (double, *)
rotation의 yaw를 나타냅니다.
##### pitch (double, *)
rotation의 pitch를 나타냅니다.

#### Example

```json
{
  "x": 0.0,
  "y": 0.0,
  "z": 0.0,
  "yaw": 0.0,
  "pitch": 0.0
}
```

## Actions
* UDP 서버는 5555 포트에서 실행됩니다.
* 인증 과정이 없으므로 외부와 통신하기 위해서는 방화벽 설정이 필요합니다.
* 모든 UUID에는 대시(-) 기호가 필요합니다.

### Serverbound
### request_entity_position
#### Parameters
##### entity_uuid (string)
대상 엔티티의 UUID를 나타냅니다.

#### Act
해당 entity가 존재할 시, 해당 entity의 위치를 전송합니다 (responded_entity_position 참고). 존재하지 않을 시, 전송되지 않습니다.

#### Example request
```json
{
  "__type__": "request_entity_position",
  "entity_uuid": "e4470236-cdb7-11ec-9d64-0242ac120002"
}
```

### teleport_entity
#### Parameters
##### entity_uuid (string)
대상 엔티티의 UUID를 나타냅니다.
##### to (position)
이동할 위치를 나타냅니다.

#### Act
해당 entity가 존재할 시, 해당 entity를 to 위치로 이동합니다.

#### Example request

```json
{
  "__type__": "teleport_entity",
  "entity_uuid": "e4470236-cdb7-11ec-9d64-0242ac120002",
  "to": {
    "x": 0.0,
    "y": 0.0,
    "z": 0.0,
    "yaw": 0.0,
    "pitch": 0.0
  }
}
```

### attack_near_entities
#### Parameters
##### entity_uuid (string)
대상 엔티티의 UUID를 나타냅니다.
##### radius (double)
대상 엔티티로부터의 공격할 엔티티까지의 반지름을 나타냅니다.
##### damage
공격할 엔티티가 받을 데미지를 설정합니다.

#### Act
해당 entity를 제외한 해당 entity로부터 주변 radius만큼의 엔티티를 공격한 뒤, 공격받은 엔티티의 수를 전송합니다. (attacked_near_entities 참고)

#### Example request

```json
{
  "__type__": "teleport_entity",
  "entity_uuid": "e4470236-cdb7-11ec-9d64-0242ac120002",
  "to": {
    "x": 0.0,
    "y": 0.0,
    "z": 0.0,
    "yaw": 0.0,
    "pitch": 0.0
  }
}
```

## Clientbound
### responded_entity_position
request_entity_position에서 반환된 position 값을 전송합니다.
#### Example respond
```json
{
  "__type__": "responded_entity_position",
  "x": 0.0,
  "y": 0.0,
  "z": 0.0,
  "yaw": 0.0,
  "pitch": 0.0
}
```

### attacked_near_entities
attack_near_entities에서 공격된 엔티티의 수를 전송합니다.
#### Fields
##### size
공격받은 엔티티의 수입니다. 0일시 아무 엔티티도 공격받지 않은 것 입니다.
#### Example respond
```json
{
  "__type__": "attacked_near_entities",
  "size": 0
}
```